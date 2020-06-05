package com.rtx.treadmill.RtxTools;

import android.graphics.Paint;
import android.text.TextUtils;
import android.widget.TextView;

import com.rtx.treadmill.RtxView.RtxTextView;

import java.util.ArrayList;

public class TextJustification {

    public static void justify(RtxTextView textView, float contentWidth) {
        String text = textView.getText().toString();
        String tempText;
        String resultText = "";

        Paint paint = textView.getPaint();
        ArrayList<String> paraList = new ArrayList<String>();
        paraList = paraBreak(text, textView);
        for (int i = 0; i < paraList.size(); i++) {
            ArrayList<String> lineList = lineBreak(paraList.get(i).trim(), paint, contentWidth);
            tempText = TextUtils.join(" ", lineList).replaceFirst("\\s*", "");
            resultText += tempText.replaceFirst("\\s*", "") + "\n";
        }
        textView.setText(resultText);
    }

    //分開每個段落
    public static ArrayList<String> paraBreak(String text, TextView textview) {
        ArrayList<String> paraList = new ArrayList<String>();
        String[] paraArray = text.split("\\n+");
        for (String para : paraArray) {
            paraList.add(para);
        }


        return paraList;
    }

    //分開每一行，使每一行填入最多的單詞數
    private static ArrayList<String> lineBreak(String text, Paint paint, float contentWidth) {
        String[] wordArray = text.split("\\s");
        ArrayList<String> lineList = new ArrayList<String>();
        String myText = "";
        for (String word : wordArray) {
            if (paint.measureText(myText + " " + word) <= contentWidth)


                myText = myText + " " + word;
            else {
                int totalSpacesToInsert = (int) ((contentWidth - paint.measureText(myText)) / paint.measureText(" "));
                lineList.add(justifyLine(myText, totalSpacesToInsert));
                myText = word;
            }
        }
        lineList.add(myText);
        return lineList;
    }


    //已填入最多單詞數的一行，插入對應的空格數直到該行滿
    private static String justifyLine(String text, int totalSpacesToInsert) {
        String[] wordArray = text.split("\\s");
        String toAppend = " ";
        while ((totalSpacesToInsert) >= (wordArray.length - 1)) {
            toAppend = toAppend + " ";
            totalSpacesToInsert = totalSpacesToInsert - (wordArray.length - 1);
        }


        int i = 0;
        String justifiedText = "";
        for (String word : wordArray) {
            if (i < totalSpacesToInsert)
                justifiedText = justifiedText + word + " " + toAppend;
            else
                justifiedText = justifiedText + word + toAppend;
            i++;
        }
        return justifiedText;
    }
}


