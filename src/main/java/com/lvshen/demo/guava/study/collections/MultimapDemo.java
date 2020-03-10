package com.lvshen.demo.guava.study.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import org.junit.Test;

import java.util.*;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/6/24 9:46
 * @since JDK 1.8
 */
public class MultimapDemo {

	@Test
	public void test() {
		Map<String, List<StudentScore>> studentScoreMap = new HashMap<>();

		String stuName = "peida";
		for (int i = 10; i < 12; i++) {
			StudentScore studentScore = new StudentScore();
			studentScore.courseId = 1001 + i;
			studentScore.score = 100 - i;

			List<StudentScore> list = studentScoreMap.get(stuName);
			if (list == null) {
				list = new ArrayList<>();
				studentScoreMap.put(stuName, list);
			}
			list.add(studentScore);
		}
		System.out.println("size = " + studentScoreMap);
		System.out.println("containsKey peida = " + studentScoreMap.containsKey("peida"));

		System.out.println("containsKey jerry = " + studentScoreMap.containsKey("jerry"));
		System.out.println("containsKey peida size = " + studentScoreMap.get("peida").size());

		List<StudentScore> StudentScoreList = studentScoreMap.get("peida");
		if (StudentScoreList != null && StudentScoreList.size() > 0) {
			for (StudentScore stuScore : StudentScoreList) {
				System.out.println("courseId:" + stuScore.courseId + " score:" + stuScore.score);
			}
		}

	}

	@Test
	public void testMultimap() {
        Multimap<String, StudentScore> scoreMultimap = ArrayListMultimap.create();
        for (int i = 10; i < 12; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.courseId = 1001 + i;
            studentScore.score = 100 - i;
            scoreMultimap.put("peida", studentScore);
        }
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());
        System.out.println("test:" + scoreMultimap);

        Collection<StudentScore> studentScores = scoreMultimap.get("peida");
        System.out.println("studentScores:" + studentScores);

        studentScores.clear();
        StudentScore studentScoreNew = new StudentScore();
        studentScoreNew.courseId = 1034;
        studentScoreNew.score = 67;
        studentScores.add(studentScoreNew);
        System.out.println("studentScores2:" + studentScores);

        StudentScore jerryStudentScore = new StudentScore();
        jerryStudentScore.courseId = 1045;
        jerryStudentScore.score = 56;
        scoreMultimap.put("jerry", jerryStudentScore);

        System.out.println("jerryStudentScore:" + jerryStudentScore);

        for (StudentScore stuScore : scoreMultimap.values()) {
            System.out.println("stuScore one:" + stuScore.courseId + " score:" + stuScore.score);
        }

        scoreMultimap.remove("jerry", jerryStudentScore);
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.get("jerry"));

        scoreMultimap.put("harry", jerryStudentScore);
        scoreMultimap.removeAll("harry");
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.get("harry"));


    }

	@Data
	class StudentScore {
		int courseId;
		int score;
	}

}
