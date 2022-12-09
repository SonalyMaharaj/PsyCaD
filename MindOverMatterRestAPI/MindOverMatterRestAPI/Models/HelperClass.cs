using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Web;

namespace MindOverMatterRestAPI.Models
{
    public class HelperClass
    {



        public static String FlagsFile { get; set; } = "C:\\INFOMANIACS\\InfomaniacsZ\\Team14\\MindOverMatterRestAPI\\MindOverMatterRestAPI\\Models\\FlagsList.txt";

        //this function checks if a given string(i.e diary entry) has flag words, and returns those flagged words
        //if false is returned then there are no flagged words
        public static String DetermineFlag(String diary)
        {
            // Read a text file line by line.  
            string[] Flags = File.ReadAllLines(FlagsFile);
            //get the diary as individual words
            string[] diaryasWords = diary.Split(' ');
            String wordsflagged = "";
            
            foreach (string line in Flags)
            {

                foreach(string word in diaryasWords) {
                    if (line.ToLower().Equals(word.ToLower())) //if the word in the flag list is found in the diary
                    {
                        wordsflagged += word+";";
                    }
                }


            }
            if (wordsflagged.Equals("")) {
                return "NONE"; //NO DIARY FLAGS FOUND
            }
            return wordsflagged;
        }

    }


}