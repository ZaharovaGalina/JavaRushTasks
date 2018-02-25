package com.javarush.task.task20.task2027;

import java.util.ArrayList;
import java.util.List;

/*
Кроссворд
*/
public class Solution {
    public static void main(String[] args) {
        int[][] crossword = new int[][]{
                {'f', 'e', 'e', 'r', 'l', 'k'},
                {'u', 's', 'a', 'm', 'e', 'o'},
                {'l', 'n', 'g', 'r', 'o', 'v'},
                {'m', 'l', 'p', 'r', 'p', 'h'},
                {'p', 'o', 'e', 'e', 'j', 'j'}
        };
        List<Word> list = detectAllWords(crossword, "home", "same");

        for (Word words : list)
            System.out.println(words.toString());
        /*
Ожидаемый результат
home - (5, 3) - (2, 0)
same - (1, 1) - (4, 1)
         */
    }

    public static List<Word> detectAllWords(int[][] crossword, String... words) {
        List<Word> list = new ArrayList<>();

        for (String word : words) {

            char[] chars = word.toCharArray();

            //Предполагаемые координаты первой буквы
            int x1 = 0;
            int y1 = 0;

            //Предполагаемые координаты последней буквы
            int x2 = 0;
            int y2 = 0;

            //Вспомонательные переменные для поиска позиций в массиве
            int n1;
            int n2;

            search:
            for (int[] arr : crossword) {
                //Поиск первой буквы
                for (int posX1 = 0; posX1 < arr.length; posX1++) {

                    n1 = searchInArr(arr, posX1, chars[0]);

                    if (n1 != -1) {
                        x1 = n1;
                        posX1 = n1;

                        //Поиск последней буквы
                        y2 = 0;
                        for (int[] arrTwo : crossword) {
                            for (int posX2 = 0; posX2 < arr.length; posX2++) {

                                n2 = searchInArr(arrTwo, posX2, chars[chars.length - 1]);
                                if (n2 != -1) {
                                    x2 = n2;
                                    posX2 = n2;
                                }

                                //Переменные для проверки всего слова
                                int absX = Math.abs(x2 - x1);
                                int absY = Math.abs(y2 - y1);
                                int lengWord = chars.length - 1;

                                //Проверка всего слова
                                research:
                                if (absX == lengWord && absY == lengWord || absX == 0 && absY == lengWord || absX == lengWord && absY == 0) {

                                    if (testWord(crossword,x1,y1,x2,y2,chars) == false)
                                        break research;

                                    Word foundWord = new Word(word);
                                    foundWord.setStartPoint(x1, y1);
                                    foundWord.setEndPoint(x2, y2);
                                    list.add(foundWord);
                                    break search;
                                }
                            }
                            y2++;
                        }
                    }
                }
                y1++;
            }
        }
        return list;
    }
    // Поиск позиции в массиве
    private static int searchInArr(int[] arr, int position, char charWord) {
        int found = -1;
        for (int i = position; i < arr.length; i++) {
            if (arr[i] == charWord) {
                found = i;
                break;
            }
        }
        return found;
    }

    //Проверка слова
    private static boolean testWord(int[][] crossword, int x1, int y1, int x2, int y2, char[] charWord) {
        for (int i = 0; i < charWord.length; i++) {
            if (x1 == x2) {
                if (y1 <= y2 && crossword[y1+i][x1] != charWord[i]) return false;
                if (y1 >= y2 && crossword[y1-i][x1] != charWord[i]) return false;
            } else if (y1 == y2) {
                if (x1 <= x2 && crossword[y1][x1+i] != charWord[i]) return false;
                if (x1 >= x2 && crossword[y1][x1-i] != charWord[i]) return false;
            } else if (x1 < x2) {
                if (y1 <= y2 && crossword[y1+i][x1+i] != charWord[i]) return false;
                if (y1 >= y2 && crossword[y1-i][x1+i] != charWord[i]) return false;
            } else {
                if (y1 <= y2 && crossword[y1+i][x1-i] != charWord[i]) return false;
                if (y1 >= y2 && crossword[y1-i][x1-i] != charWord[i]) return false;
            }
        }
        return true;
    }

    public static class Word {
        private String text;
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        public Word(String text) {
            this.text = text;
        }

        public void setStartPoint(int i, int j) {
            startX = i;
            startY = j;
        }

        public void setEndPoint(int i, int j) {
            endX = i;
            endY = j;
        }

        @Override
        public String toString() {
            return String.format("%s - (%d, %d) - (%d, %d)", text, startX, startY, endX, endY);
        }
    }
}