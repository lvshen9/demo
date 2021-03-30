package com.lvshen.demo.arithmetic.sensitiveword.toolgoods;

import com.lvshen.demo.arithmetic.sensitiveword.toolgoods.internals.BaseMatch;
import com.lvshen.demo.arithmetic.sensitiveword.toolgoods.internals.TrieNode3;

import java.util.ArrayList;
import java.util.List;


/**
 * 文本搜索匹配, ,支持 部分 正则 如 . ? [ ] \ ( | ) ,不支持( )内再嵌套( )
 */
public class WordsMatch extends BaseMatch {

    /**
     * 在文本中查找第一个关键字
     * 
     * @param text 文本
     * @return
     */
    public WordsSearchResult FindFirst(final String text) {
        TrieNode3 ptr = null;
        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);

            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        final WordsSearchResult result = FindFirst(text, i + 1, ptr.WildcardNode);
                        if (result != null) {
                            return result;
                        }
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    final Integer r = tn.Results.get(0);
                    final int length = _keywordLength[r];
                    final int start = i - length + 1;
                    if (start >= 0) {
                        final int kIndex = _keywordIndex[r];
                        final String matchKeyword = _matchKeywords[kIndex];
                        final String keyword = text.substring(start, i + 1);
                        return new WordsSearchResult(keyword, start, i, kIndex, matchKeyword);
                    }
                }
            }
            ptr = tn;
        }
        return null;
    }

    private WordsSearchResult FindFirst(final String text, final int index, TrieNode3 ptr) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    final WordsSearchResult result = FindFirst(text, i + 1, ptr.WildcardNode);
                    if (result != null) {
                        return result;
                    }
                }
                return null;
            }
            tn = ptr.GetValue(t);

            if (tn.End) {
                final Integer r = tn.Results.get(0);
                final int length = _keywordLength[r];
                final int start = i - length + 1;
                if (start >= 0) {
                    final int kIndex = _keywordIndex[r];
                    final String matchKeyword = _matchKeywords[kIndex];
                    final String keyword = text.substring(start, i + 1);
                    return new WordsSearchResult(keyword, start, i, kIndex, matchKeyword);
                }
            }
            ptr = tn;
        }
        return null;
    }

    /**
     * 在文本中查找所有的关键字
     * 
     * @param text 文本
     * @return
     */
    public List<WordsSearchResult> FindAll(final String text) {
        TrieNode3 ptr = null;
        final List<WordsSearchResult> result = new ArrayList<WordsSearchResult>();

        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        FindAll(text, i + 1, ptr.WildcardNode, result);
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    for (final Integer r : tn.Results) {
                        final int length = _keywordLength[r];
                        final int start = i - length + 1;
                        if (start >= 0) {
                            final int kIndex = _keywordIndex[r];
                            final String matchKeyword = _matchKeywords[kIndex];
                            final String keyword = text.substring(start, i + 1);
                            final WordsSearchResult wr = new WordsSearchResult(keyword, start, i, kIndex, matchKeyword);
                            result.add(wr);
                        }
                    }
                }
            }
            ptr = tn;
        }
        return result;
    }

    private void FindAll(final String text, final int index, TrieNode3 ptr, final List<WordsSearchResult> result) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    FindAll(text, i + 1, ptr.WildcardNode, result);
                }
                return;
            } else {
                tn = ptr.GetValue(t);
            }
            if (tn.End) {
                for (final Integer r : tn.Results) {
                    final int length = _keywordLength[r];
                    final int start = i - length + 1;
                    if (start >= 0) {
                        final int kIndex = _keywordIndex[r];
                        final String matchKeyword = _matchKeywords[kIndex];
                        final String keyword = text.substring(start, i + 1);
                        final WordsSearchResult wr = new WordsSearchResult(keyword, start, i, kIndex, matchKeyword);
                        result.add(wr);
                    }
                }
            }
            ptr = tn;
        }
    }

    /**
     * 判断文本是否包含关键字
     * 
     * @param text 文本
     * @return
     */
    public boolean ContainsAny(final String text) {
        TrieNode3 ptr = null;
        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        final boolean result = ContainsAny(text, i + 1, ptr.WildcardNode);
                        if (result) {
                            return true;
                        }
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    final int length = _keywordLength[tn.Results.get(0)];
                    final int s = i - length + 1;
                    if (s >= 0) {
                        return true;
                    }
                }
            }
            ptr = tn;
        }
        return false;
    }

    private boolean ContainsAny(final String text, final int index, TrieNode3 ptr) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    return ContainsAny(text, i + 1, ptr.WildcardNode);
                }
                return false;
            }
            tn = ptr.GetValue(t);

            if (tn.End) {
                final int length = _keywordLength[tn.Results.get(0)];
                final int s = i - length + 1;
                if (s >= 0) {
                    return true;
                }
            }
            ptr = tn;
        }
        return false;
    }

    /**
     * 在文本中替换所有的关键字, 替换符默认为 *
     * 
     * @param text 文本
     * @return
     */
    public String Replace(final String text) {
        return Replace(text, '*');
    }

    /**
     * 在文本中替换所有的关键字
     * 
     * @param text        文本
     * @param replaceChar 替换符
     * @return
     */
    public String Replace(final String text, final char replaceChar) {
        final StringBuilder result = new StringBuilder(text);

        TrieNode3 ptr = null;
        for (int i = 0; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr == null) {
                tn = _first[t];
            } else {
                if (ptr.HasKey(t) == false) {
                    if (ptr.HasWildcard) {
                        Replace(text, i + 1, ptr.WildcardNode, replaceChar, result);
                    }
                    tn = _first[t];
                } else {
                    tn = ptr.GetValue(t);
                }
            }
            if (tn != null) {
                if (tn.End) {
                    final int maxLength = _keywordLength[tn.Results.get(0)];
                    final int start = i + 1 - maxLength;
                    if (start >= 0) {
                        for (int j = start; j <= i; j++) {
                            result.setCharAt(j, replaceChar);
                        }
                    }
                }
            }
            ptr = tn;
        }
        return result.toString();
    }

    private void Replace(final String text, final int index, TrieNode3 ptr, final char replaceChar,
            final StringBuilder result) {
        for (int i = index; i < text.length(); i++) {
            final char t = text.charAt(i);
            TrieNode3 tn;
            if (ptr.HasKey(t) == false) {
                if (ptr.HasWildcard) {
                    Replace(text, i + 1, ptr.WildcardNode, replaceChar, result);
                }
                return;
            }
            tn = ptr.GetValue(t);
            if (tn.End) {
                final int maxLength = _keywordLength[tn.Results.get(0)];
                final int start = i + 1 - maxLength;
                if (start >= 0) {
                    for (int j = start; j <= i; j++) {
                        result.setCharAt(j, replaceChar);
                    }
                }
            }
            ptr = tn;
        }
    }
}