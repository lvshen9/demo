package com.lvshen.demo.java8.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lvshen.demo.catchexception.BusinessException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2024/3/31 17:47
 * @since JDK 1.8
 */
@Slf4j
public class MyFileUtils {

    private static final ExecutorService excelThreadPool = (ExecutorService) MySpringContextHolder.getBean("excelThreadPool");

    /**
     * 输入流转文件
     * @param ins
     * @param name
     * @return
     * @throws Exception
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int len = 8192;
            byte[] buffer = new byte[len];
            int bytesRead;
            while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != os) {
                os.close();
            }
            ins.close();
        }
        return file;
    }

    public static List<File> zipByte2File(byte[] zipData) {
        List<File> fileList = new ArrayList<>();

        File tempFile;
        try {
            tempFile = File.createTempFile("temp", ".zip");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(zipData);
            }
            fileList.add(tempFile);
            return fileList;
        } catch (IOException e) {
            throw new BusinessException("文件写入错误", e);
        }
    }

    /**
     * 文件地址转输入流
     *
     * @param fileUrl
     * @return
     */
    @SneakyThrows
    private static HttpURLConnection url2Stream(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return null;
        }
        return (HttpURLConnection) new URL(fileUrl).openConnection();
    }

    @SneakyThrows
    public static MultipartFile toMultipartFile(String fileUrl, String fileName) {
        HttpURLConnection httpUrl = url2Stream(fileUrl);
        httpUrl.connect();
        InputStream inputStream = httpUrl.getInputStream();
        File newFile = MyFileUtils.inputStreamToFile(inputStream, fileName);
        httpUrl.disconnect();
        //todo 这里要将file转成MultipartFile
        return null;

    }

    /**
     * 将输出流写到本地
     *
     * @param byteArrayOutputStream
     * @param filePath
     */
    @SneakyThrows
    public static void writeFileToLocal(ByteArrayOutputStream byteArrayOutputStream, String filePath) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(byteArray);
        fileOutputStream.close();
    }

    /**
     * 文件转zip
     *
     * @param files
     * @param zipName
     */
    public static void listFileToZip(List<File> files, String zipName) {
        if (StringUtils.isBlank(zipName)) {
            zipName = "files.zip";
        }
        try {
            FileOutputStream fos = new FileOutputStream(zipName);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for (File file : files) {
                addToZip(file, zos);
            }

            zos.close();
            fos.close();
            log.info("Files zipped successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addToZip(File file, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }

    /**
     * 输出流转输入流
     *
     * @param outputStream
     * @return
     */
    public static InputStream convertOutputStreamToInputStream(OutputStream outputStream) {
        ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) outputStream;
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 批量压缩文件 v4.0
     *
     * @param fileNameList 需要压缩的文件名称列表(包含相对路径)
     * @param zipOutName   压缩后的文件名称
     **/
    @SneakyThrows
    public static void compressFileList(String zipOutName, List<String> fileNameList) {

        ParallelScatterZipCreator parallelScatterZipCreator = new ParallelScatterZipCreator(excelThreadPool);
        OutputStream outputStream = new FileOutputStream(zipOutName);
        ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(outputStream);
        zipArchiveOutputStream.setEncoding("UTF-8");
        for (String fileName : fileNameList) {
            File inFile = new File(fileName);
            final InputStreamSupplier inputStreamSupplier = () -> {
                try {
                    return new FileInputStream(inFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return new NullInputStream(0);
                }
            };
            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(inFile.getName());
            zipArchiveEntry.setMethod(ZipArchiveEntry.DEFLATED);
            zipArchiveEntry.setSize(inFile.length());
            zipArchiveEntry.setUnixMode(UnixStat.FILE_FLAG | 436);
            parallelScatterZipCreator.addArchiveEntry(zipArchiveEntry, inputStreamSupplier);
        }
        parallelScatterZipCreator.writeTo(zipArchiveOutputStream);
        zipArchiveOutputStream.close();
        outputStream.close();
        log.info("ParallelCompressUtil->ParallelCompressUtil-> info:{}", JSONObject.toJSONString(parallelScatterZipCreator.getStatisticsMessage()));
    }

    @Bean("excelThreadPool")
    public ExecutorService buildExcelThreadPool() {
        int cpuNum = Runtime.getRuntime().availableProcessors();
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(1000);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("srm-sass-document-excel-pool-%d").build();
        return new ThreadPoolExecutor(10 * cpuNum, 30 * cpuNum,
                40, TimeUnit.MINUTES, workQueue, threadFactory);
    }
}

