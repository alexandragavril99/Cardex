package com.example.proiect_apostu_gavril_1081;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.util.List;

public class PieChart extends View {
    List<Feedback> feedbackList;
    int width;
    public PieChart(Context context, List<Feedback> feedbackList, int width)
    {
        super(context);
        this.feedbackList = feedbackList;
        this.width = width;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {

        // calculam numarul de raspunsuri negative pentru recomandare
        int noNo =0;
        for(int i=0; i<feedbackList.size(); i++)
            if(!feedbackList.get(i).isRecommend()) {
                noNo++;
            }

        // salvam numarul de recenzii pentru fiecare interval de stele
        int starsArray[] = new int[5];
        for(int i=0; i<feedbackList.size(); i++) {
            if(feedbackList.get(i).getNoStars() <= 1) {
                starsArray[0] ++;
            } else if(feedbackList.get(i).getNoStars() <=2) {
                starsArray[1]++;
            } else if(feedbackList.get(i).getNoStars() <=3) {
                starsArray[2]++;
            } else if(feedbackList.get(i).getNoStars() <=4) {
                starsArray[3]++;
            } else starsArray[4]++;
        }

        Typeface customTypeface = getResources().getFont(R.font.poppins_semibold);

        Paint instrumentScriere = new Paint();
        instrumentScriere.setColor(Color.BLACK);
        instrumentScriere.setTextSize(35);
        instrumentScriere.setTextAlign(Paint.Align.LEFT);
        instrumentScriere.setTypeface(customTypeface);
        Paint instrument=new Paint();
        instrument.setStyle(Paint.Style.FILL);


        canvas.drawText("Would users recommend this app?",150,100,instrumentScriere);

        // desenam piechart-ul si patratele pentru legenda

        int unghi = 360 * noNo/feedbackList.size();

        instrument.setColor(Color.rgb(128,157,165));
        canvas.drawArc(180,180, 500, 480, 0, unghi, true,instrument);
        canvas.drawRect(580,200,620,240,instrument);
        instrument.setColor(Color.rgb(8,75,120));
        canvas.drawArc(180,180, 500, 480, unghi, 360-unghi,true,instrument);
        canvas.drawRect(580,270,620,310,instrument);

        // adaugam textul pentru legenda

        canvas.drawText("Yes",640,233,instrumentScriere);
        canvas.drawText("No",640,303,instrumentScriere);
        float percentage = (float) noNo/feedbackList.size();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        canvas.drawText("Yes:"+" "+decimalFormat.format(percentage),580,410,instrumentScriere);
        percentage=1-percentage;
        canvas.drawText("No: "+ "  "+decimalFormat.format(percentage),580,460,instrumentScriere);


        canvas.drawText("How many stars do users give us?",150,700,instrumentScriere);


        int barChartWidth = 50; // dimeniunea unei bare din grafic

        int maxValue = 0;
        for(int i = 0; i<starsArray.length; i++){
            maxValue += starsArray[i];
        }

        instrument.setColor(Color.rgb(91,147,162));

        // desenam barchart-ul

        for(int i = 0 ; i<starsArray.length; i++) {
            canvas.drawText(i + "-" + (i+1),  200, 840+ i*25+i*50,instrumentScriere);
            canvas.drawRect(300,800 + i * barChartWidth/2 + i * barChartWidth,315 + (width-415)/maxValue * starsArray[i],
                    850 + i*barChartWidth/2 + i * barChartWidth,instrument);
        }
    }
}

