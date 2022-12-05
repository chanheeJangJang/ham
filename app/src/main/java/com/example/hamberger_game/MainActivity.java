package com.example.hamberger_game;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
 ////수정됐나?
    ImageButton bread_top_btn,bread_bottom_btn,cheese_btn,meat_btn,onion_btn,tomato_btn,egg_btn,lettuce_btn;
    ImageView[] recipe=new ImageView[11];
    ImageView[] my_recipe=new ImageView[11];
    Button retry_btn;
    TextView success_Count_txt,timer_txt;

    public static final int[] materials={R.drawable.cheese,R.drawable.s,R.drawable.egg,R.drawable.meat,R.drawable.lettuce,R.drawable.tomato}; ////////onion
    public  int max_order_pattyCount=2;//난이도 조절을 위한 최대 주문 패티의 개수
    public int order_pattyCount=0;//아래빵부터 위빵까지 패티의 총 개수
    public int pattyCount=0;//내가 패티 버튼을 누를때마다 1씩 증가
    public Integer success_Count=0;//성공 횟수
    public Integer timer_Count=20;

    //패티들의 이미지소스 배열의 인덱스번호를 String형식으로 이어 붙여 그 햄버거 만의 코드를 생성한다.
    public String recipe_Code="";
    public String my_recipe_Code="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("햄버거 만들기 게임");

        success_Count_txt=(TextView)findViewById(R.id.Success_Count_txt);
        timer_txt=(TextView)findViewById(R.id.time_txt) ;

        bread_top_btn=(ImageButton)findViewById(R.id.bread_top_btn);
        bread_bottom_btn=(ImageButton)findViewById(R.id.s_btn); //////
        cheese_btn=(ImageButton)findViewById(R.id.cheese_btn);
        meat_btn=(ImageButton)findViewById(R.id.meat_btn);
        onion_btn=(ImageButton)findViewById(R.id.c_btn);//////////onion
        tomato_btn=(ImageButton)findViewById(R.id.tomato_btn);
        egg_btn=(ImageButton)findViewById(R.id.egg_btn);
        lettuce_btn=(ImageButton)findViewById(R.id.lettuce_btn);

        retry_btn=(Button)findViewById(R.id.retry_btn);

        recipe[0]=(ImageView)findViewById(R.id.recipe0);
        recipe[1]=(ImageView)findViewById(R.id.recipe1);
        recipe[2]=(ImageView)findViewById(R.id.recipe2);
        recipe[3]=(ImageView)findViewById(R.id.recipe3);
        recipe[4]=(ImageView)findViewById(R.id.recipe4);
        recipe[5]=(ImageView)findViewById(R.id.recipe5);
        recipe[6]=(ImageView)findViewById(R.id.recipe6);
        recipe[7]=(ImageView)findViewById(R.id.recipe7);
        recipe[8]=(ImageView)findViewById(R.id.recipe8);
        recipe[9]=(ImageView)findViewById(R.id.recipe9);
        recipe[10]=(ImageView)findViewById(R.id.recipe10);

        my_recipe[0]=(ImageView)findViewById(R.id.my_recipe0);
        my_recipe[1]=(ImageView)findViewById(R.id.my_recipe1);
        my_recipe[2]=(ImageView)findViewById(R.id.my_recipe2);
        my_recipe[3]=(ImageView)findViewById(R.id.my_recipe3);
        my_recipe[4]=(ImageView)findViewById(R.id.my_recipe4);
        my_recipe[5]=(ImageView)findViewById(R.id.my_recipe5);
        my_recipe[6]=(ImageView)findViewById(R.id.my_recipe6);
        my_recipe[7]=(ImageView)findViewById(R.id.my_recipe7);
        my_recipe[8]=(ImageView)findViewById(R.id.my_recipe8);
        my_recipe[9]=(ImageView)findViewById(R.id.my_recipe9);
        my_recipe[10]=(ImageView)findViewById(R.id.my_recipe10);

        AlertDialog.Builder gamestart_dlg=new AlertDialog.Builder(MainActivity.this);
        gamestart_dlg.setTitle("게임 시작");
        gamestart_dlg.setMessage("시작을 누르면 게임을 시작합니다.");
        gamestart_dlg.setPositiveButton("시작", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer_Start();
                createRecipe();
            }
        });
        gamestart_dlg.show();

       bread_top_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   my_recipe[pattyCount].setImageResource(R.drawable.bread_top);
                   my_recipe_Code+="B";
                   pattyCount++;
               if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                   if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                   {
                       success_Count++;
                       success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                       if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                       {
                           max_order_pattyCount++;
                       }

                       timer_Count+=2;//성공시 카운터 2초 증가

                       Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                       tMsg.show();
                   }
                   else
                   {
                       timer_Count-=1;//실패시 카운터 1초 감소
                       Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                       //Toast tMsg = Toast.makeText(MainActivity.this,recipe_Code,Toast.LENGTH_SHORT);
                       tMsg.show();
                   }

                   //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                   createRecipe();
               }
           }
       });

       bread_bottom_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                   my_recipe[pattyCount].setImageResource(R.drawable.s);/////////bottom
                   my_recipe_Code+="B";
                   pattyCount++;
               if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                   if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                   {
                       success_Count++;
                       success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                       if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                       {
                           max_order_pattyCount++;
                       }

                       timer_Count+=2;//성공시 카운터 2초 증가

                       Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                       tMsg.show();
                   }
                   else
                   {
                       timer_Count-=1;//실패시 카운터 1초 감소

                       Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                       tMsg.show();
                   }

                   //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                   createRecipe();
               }
           }
       });

        cheese_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    my_recipe[pattyCount].setImageResource(materials[0]);
                    my_recipe_Code+=Integer.toString(0);
                    pattyCount++;
                if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                    if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                    {
                        success_Count++;
                        success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                        if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                        {
                            max_order_pattyCount++;
                        }

                        timer_Count+=2;//성공시 카운터 2초 증가

                        Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }
                    else
                    {
                        timer_Count-=1;//실패시 카운터 1초 감소

                        Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }

                    //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                    createRecipe();
                }
            }
        });

        onion_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    my_recipe[pattyCount].setImageResource(materials[1]);
                    my_recipe_Code+=Integer.toString(1);
                    pattyCount++;
                if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                    if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                    {
                        success_Count++;
                        success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                        if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                        {
                            max_order_pattyCount++;
                        }

                        timer_Count+=2;//성공시 카운터 2초 증가

                        Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }
                    else
                    {
                        timer_Count-=1;//실패시 카운터 1초 감소

                        Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }

                    //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                    createRecipe();
                }
            }
        });

        egg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    my_recipe[pattyCount].setImageResource(materials[2]);
                    my_recipe_Code+=Integer.toString(2);
                    pattyCount++;
                if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                    if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                    {
                        success_Count++;
                        success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                        if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                        {
                            max_order_pattyCount++;
                        }

                        timer_Count+=2;//성공시 카운터 2초 증가

                        Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }
                    else
                    {
                        timer_Count-=1;//실패시 카운터 1초 감소

                        Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }

                    //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                    createRecipe();
                }
            }
        });

        meat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    my_recipe[pattyCount].setImageResource(materials[3]);
                    my_recipe_Code+=Integer.toString(3);
                    pattyCount++;
                if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                    if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                    {
                        success_Count++;
                        success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                        if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                        {
                            max_order_pattyCount++;
                        }

                        timer_Count+=2;//성공시 카운터 2초 증가

                        Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }
                    else
                    {
                        timer_Count-=1;//실패시 카운터 1초 감소

                        Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }

                    //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                    createRecipe();
                }
            }
        });

        lettuce_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    my_recipe[pattyCount].setImageResource(materials[4]);
                    my_recipe_Code+=Integer.toString(4);
                    pattyCount++;
                if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                    if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                    {
                        success_Count++;
                        success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                        if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                        {
                            max_order_pattyCount++;
                        }

                        timer_Count+=2;//성공시 카운터 2초 증가

                        Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }
                    else
                    {
                        timer_Count-=1;//실패시 카운터 1초 감소

                        Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }

                    //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                    createRecipe();
                }
            }
        });

        tomato_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    my_recipe[pattyCount].setImageResource(materials[5]);
                    my_recipe_Code+=Integer.toString(5);
                    pattyCount++;
                if(pattyCount==order_pattyCount) {//주문 햄버거의 패티갯수와 내가 제작한 패티갯수가 같을 경우
                    if (recipe_Code.equals(my_recipe_Code))//주문 햄버거의 레시피 코드와 내가 제작한 햄버거의 레시피 코드가 같을 경우
                    {
                        success_Count++;
                        success_Count_txt.setText("성공 횟수 : "+success_Count.toString());
                        if((success_Count%3)==0&&(max_order_pattyCount<8))//성공 횟수가 3의 배수이고 최대 패티의 개수가 8미만일 경우
                        {
                            max_order_pattyCount++;
                        }

                        timer_Count+=2;//성공시 카운터 2초 증가

                        Toast tMsg = Toast.makeText(MainActivity.this,"성공!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }
                    else
                    {
                        timer_Count-=1;//실패시 카운터 1초 감소

                        Toast tMsg = Toast.makeText(MainActivity.this,"실패!!",Toast.LENGTH_SHORT);
                        tMsg.show();
                    }

                    //햄버거 제작이 완료 되면 새로운 레시피를 받아온다.
                    createRecipe();
                }
            }
        });

        retry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pattyCount=0;
                my_recipe_Code="";

                //내가 제작할 햄거버의 이미지 비우기
                for(int i=0; i<my_recipe.length;i++)
                {
                    my_recipe[i].setImageResource(0);
                }
            }
        });
    }

    public  void timer_Start()
    {
        max_order_pattyCount=2;
        success_Count=0;
        timer_Count=20;

        final Timer timer=new Timer();

        final TimerTask tt=new TimerTask() {
            @Override
            public void run() {
                timer_Count--;

                    MainActivity.this.runOnUiThread(new Runnable() {//타이머에서 ui를 수정해야 할경우 runOnUiThread를 사용해야함
                        @Override
                        public void run() {
                            if(timer_Count<0) {//타이머가 0 미만이 될 경우
                                timer.cancel();

                                AlertDialog.Builder gameover_dlg=new AlertDialog.Builder(MainActivity.this);
                                gameover_dlg.setTitle("게임 종료");
                                gameover_dlg.setMessage("만든 갯수 : "+success_Count.toString());
                                gameover_dlg.setPositiveButton("다시 시작", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        timer_Start();
                                        createRecipe();
                                    }
                                });
                                gameover_dlg.show();
                            }
                            else
                            {
                                timer_txt.setText("남은 시간 : " + timer_Count.toString());
                            }
                        }
                    });

            }
        };

        timer.schedule(tt,0,1000);
    }

    public void createRecipe()
    {
        /*변수 초기화*/
        order_pattyCount=0;
        pattyCount=0;
        recipe_Code="";
        my_recipe_Code="";

        //주문 햄거버의 이미지 비우기
        for(int i=0; i<my_recipe.length;i++)
        {
            recipe[i].setImageResource(0);
        }

        //내가 제작할 햄거버의 이미지 비우기
        for(int i=0; i<my_recipe.length;i++)
        {
            my_recipe[i].setImageResource(0);
        }

        //아래빵
        recipe[0].setImageResource(R.drawable.s);/////bottom
        recipe_Code+="B";

        Random ran=new Random();
        order_pattyCount++;

        //패티
        int ranPattyCount=ran.nextInt(max_order_pattyCount)+1; //랜덤으로 빵사이의 패티 갯수를 정함.
        for(int i=1;i<=ranPattyCount;i++)
        {
            int ranNum=ran.nextInt(materials.length);
            recipe[i].setImageResource(materials[ranNum]);
            recipe_Code+=Integer.toString(ranNum);
            order_pattyCount++;
        }

        //윗빵
        recipe[ranPattyCount+1].setImageResource(R.drawable.bread_top);
        recipe_Code+="B";

        order_pattyCount++;
    }
}
