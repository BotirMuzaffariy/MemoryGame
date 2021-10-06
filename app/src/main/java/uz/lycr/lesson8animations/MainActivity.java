package uz.lycr.lesson8animations;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uz.lycr.lesson8animations.models.CardModel;

public class MainActivity extends AppCompatActivity {

    private LinearLayout endLinearLayout;
    private LinearLayout gameLinearLayout;

    ImageView img1, img2;

    // Qatorlar va Ustunlar soni
    private final int row = 5;
    private final int column = 4;

    private int openingImages = 0;
    private int visibleImages = row * column;

    private boolean isImg1 = false;
    private boolean isImg2 = true;
    private boolean isClose = false;

    private final List<CardModel> cardList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Navigatsiya paneliga rang beramiz
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.for_bars));
        }

        endLinearLayout = new LinearLayout(this);
        gameLinearLayout = new LinearLayout(this);
        LinearLayout topLinearLayout = new LinearLayout(this);

        ViewGroup.LayoutParams mainLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // MainLayout-ga rang va orientatsiya beramiz
        topLinearLayout.setOrientation(LinearLayout.VERTICAL);
        topLinearLayout.setBackgroundColor(getResources().getColor(R.color.whiter));

        // GameLayout va EndLayout-ga rang va orientatsiya beramiz
        endLinearLayout.setOrientation(LinearLayout.VERTICAL);
        gameLinearLayout.setOrientation(LinearLayout.VERTICAL);
        endLinearLayout.setBackgroundColor(getResources().getColor(R.color.main_background));
        gameLinearLayout.setBackgroundColor(getResources().getColor(R.color.main_background));

        topLinearLayout.addView(gameLinearLayout, mainLayoutParams);
        topLinearLayout.addView(endLinearLayout, mainLayoutParams);

        setContentView(topLinearLayout, mainLayoutParams);

        // GameLayout-ning UI-ini yasaymiz
        makeGameUI(gameLinearLayout);

        // EndLayout-ning UI-ini yasaymiz
        makeGameEndUI(endLinearLayout);

        // List-ni to`ldiramiz
        loadList();
    }

    private void loadList() {
        for (int i = 0; i < visibleImages; i++) {
            cardList.add(new CardModel(i, pickImage()));
        }

    }

    private void makeGameUI(LinearLayout parentLayout) {
        ImageView iv;
        LinearLayout innerLl;

        TextView topTv = new TextView(this);
        LinearLayout mainLl = new LinearLayout(this);

        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams topTvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams mainLlParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout.LayoutParams innerLlParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // TexView
        topTvParams.weight = 5.5f;
        topTv.setTextSize(50f);
        topTv.setGravity(Gravity.CENTER);
        topTv.setBackgroundResource(R.drawable.bg_for_top_tv);
        topTv.setText("<" + getString(R.string.app_name) + ">");
        topTv.setTextColor(getResources().getColor(R.color.tv_top));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            topTv.setElevation(4f);
        }
        topTv.setTypeface(ResourcesCompat.getFont(this, R.font.tribal));

        // Asosiy LinearLayout
        mainLlParams.weight = 1f;
        mainLl.setOrientation(LinearLayout.VERTICAL);
        mainLl.setPadding((int) getResources().getDimension(R.dimen.main_layout_padding_h), (int) getResources().getDimension(R.dimen.main_layout_padding_v), (int) getResources().getDimension(R.dimen.main_layout_padding_h), (int) getResources().getDimension(R.dimen.main_layout_padding_v));

        // Rasmlar
        ivParams.weight = 1f;
        ivParams.leftMargin = (int) getResources().getDimension(R.dimen.images_interval);
        ivParams.rightMargin = (int) getResources().getDimension(R.dimen.images_interval);

        // Ichki LinearLayout-lar
        innerLlParams.weight = 1f;
        innerLlParams.topMargin = (int) getResources().getDimension(R.dimen.images_interval);
        innerLlParams.bottomMargin = (int) getResources().getDimension(R.dimen.images_interval);

        int forImgId = -1;

        // Ichki LinearLayout-larni yaratamiz
        for (int i = 0; i < row; i++) {
            innerLl = new LinearLayout(this);
            innerLl.setOrientation(LinearLayout.HORIZONTAL);

            // Rasmlarni yaratamiz
            for (int j = 0; j < column; j++) {
                forImgId++;
                iv = new ImageView(this);
                iv.setImageResource(R.drawable.img_for_close);
                iv.setId(forImgId);
                ImageView finalIv = iv;
                iv.setOnClickListener(v -> setImgOnClickFunction(finalIv));
                iv.setBackgroundResource(R.drawable.bg_for_img_close);

                // Ichki LinearLayout-ga qo`shamiz
                innerLl.addView(iv, ivParams);
            }

            // Asosiy LinearLayout-ga qo`shamiz
            mainLl.addView(innerLl, innerLlParams);
        }

        // Ekranga chiqaramiz
        parentLayout.addView(topTv, topTvParams);
        parentLayout.addView(mainLl, mainLlParams);
    }

    private void makeGameEndUI(LinearLayout parentLayout) {
        TextView tvEnd = new TextView(this);
        Button btnRestart = new Button(this);
        ConstraintLayout constraintLayout = new ConstraintLayout(this);

        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ConstraintLayout.LayoutParams btnParams = new ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);

        viewParams.weight = 1;

        // TextView
        tvEnd.setTextSize(55f);
        tvEnd.setText("[  You win  ]");
        tvEnd.setTextColor(getResources().getColor(R.color.for_bars));
        tvEnd.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        tvEnd.setTypeface(ResourcesCompat.getFont(this, R.font.tribal));

        // Button Params
        btnParams.verticalBias = 0.05f;
        btnParams.matchConstraintPercentWidth = 0.9f;
        btnParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        btnParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        btnParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        btnParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;

        // Button
        btnRestart.setTextSize(20f);
        btnRestart.setText("Restart");
        btnRestart.setBackgroundResource(R.drawable.bg_for_btn);
        btnRestart.setOnClickListener(v -> setBtnOnClickFunction());
        btnRestart.setTextColor(getResources().getColor(R.color.tv_top));
        btnRestart.setPadding(0, (int) getResources().getDimension(R.dimen.btn_padding_v), 0, (int) getResources().getDimension(R.dimen.btn_padding_v));

        // Button-ni ConstrainLayout-ga qo`shamiz
        constraintLayout.addView(btnRestart, btnParams);

        // Ekranga chiqaramiz
        parentLayout.addView(tvEnd, viewParams);
        parentLayout.addView(constraintLayout, viewParams);
    }

    private void setImgOnClickFunction(ImageView imageView) {
        Animation animOpen = AnimationUtils.loadAnimation(this, R.anim.anim_open);
        Animation animClose = AnimationUtils.loadAnimation(this, R.anim.anim_close);
        Animation animInvisible = AnimationUtils.loadAnimation(this, R.anim.anim_invisible);

        isImg1 = !isImg1;
        isImg2 = !isImg2;

        imageView.startAnimation(animOpen);

        animOpen.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setEnabledAll(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isClose) {
                    img1.startAnimation(animClose);
                    img2.startAnimation(animClose);
                } else {
                    if (isImg1) {
                        openingImages++;
                        img1 = findViewById(imageView.getId());
                        isImg1 = true;
                        isImg2 = false;
                    } else if (isImg2) {
                        openingImages++;
                        img2 = findViewById(imageView.getId());
                        isImg1 = false;
                        isImg2 = true;
                    }
                    imageView.setBackgroundResource(R.drawable.bg_for_img);
                    imageView.setImageResource(cardList.get(imageView.getId()).getImgResourceId());
                }
                imageView.startAnimation(animClose);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animClose.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isClose) {
                    img2.setImageResource(R.drawable.img_for_close);
                    img2.setBackgroundResource(R.drawable.bg_for_img_close);

                    img1.setImageResource(R.drawable.img_for_close);
                    img1.setBackgroundResource(R.drawable.bg_for_img_close);

                    isImg1 = false;
                    isImg2 = true;
                    isClose = false;
                }

                if (openingImages == 2 && (img1 != null && img2 != null) && img1.getId() == img2.getId()) {
                    img2 = null;
                    img1.setBackgroundResource(R.drawable.bg_for_img_close);
                    img1.setImageResource(R.drawable.img_for_close);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setEnabledAll(true);
                try {
                    for (int i = 0; i < 20; i++) {
                        Thread.sleep(5);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (openingImages == 2) {
                    if (img2 != null) {
                        if (cardList.get(img1.getId()).getImgResourceId() == cardList.get(img2.getId()).getImgResourceId()) {
                            img1.startAnimation(animInvisible);
                            img2.startAnimation(animInvisible);
                        } else {
                            isClose = true;
                            img1.startAnimation(animOpen);
                            img2.startAnimation(animOpen);
                        }
                    }
                    openingImages = 0;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animInvisible.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                imageView.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.setClickable(true);
                img1.setVisibility(View.INVISIBLE);
                img2.setVisibility(View.INVISIBLE);

                visibleImages -= 2;

                if (visibleImages == 0) {
                    Animation animOpenLayout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_open_layout);
                    Animation animCloseLayout = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_close_layout);

                    gameLinearLayout.startAnimation(animCloseLayout);

                    animCloseLayout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            endLinearLayout.setClickable(false);
                            gameLinearLayout.setClickable(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            endLinearLayout.startAnimation(animOpenLayout);

                            gameLinearLayout.setVisibility(View.GONE);
                            endLinearLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    animOpenLayout.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            endLinearLayout.setClickable(true);
                            gameLinearLayout.setClickable(true);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void setBtnOnClickFunction() {
        restartGame(endLinearLayout, gameLinearLayout);
    }

    private void restartGame(LinearLayout disappearingLayout, LinearLayout mustBeBuildLayout) {
        Animation openLayoutAnim = AnimationUtils.loadAnimation(this, R.anim.anim_open_layout);
        Animation closeLayoutAnim = AnimationUtils.loadAnimation(this, R.anim.anim_close_layout);

        endLinearLayout.startAnimation(closeLayoutAnim);

        closeLayoutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                visibleImages = row * column;

                // Listni tozalaymiz
                cardList.clear();
                loadList();

                isImg1 = false;
                isImg2 = true;
                isClose = false;

                // Barcha View-larni o`chiramiz va o`yin UI-ni qaytadan yasaymiz
                mustBeBuildLayout.removeAllViews();
                makeGameUI(mustBeBuildLayout);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                gameLinearLayout.startAnimation(openLayoutAnim);

                // Layout-larni almashtiramiz
                disappearingLayout.setVisibility(View.GONE);
                mustBeBuildLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private int pickImage() {
        int result = -1;
        int count = 0;

        Random random = new Random();
        int num = random.nextInt(10);

        switch (num) {
            case 0:
                result = R.drawable.img_1;
                break;
            case 1:
                result = R.drawable.img_2;
                break;
            case 2:
                result = R.drawable.img_3;
                break;
            case 3:
                result = R.drawable.img_4;
                break;
            case 4:
                result = R.drawable.img_5;
                break;
            case 5:
                result = R.drawable.img_6;
                break;
            case 6:
                result = R.drawable.img_7;
                break;
            case 7:
                result = R.drawable.img_8;
                break;
            case 8:
                result = R.drawable.img_9;
                break;
            case 9:
                result = R.drawable.img_10;
                break;
        }

        for (CardModel cardModel : cardList) {
            if (cardModel.getImgResourceId() == result) {
                count++;
            }
        }

        if (count < 2) {
            return result;
        } else {
            return pickImage();
        }
    }

    private void setEnabledAll(boolean type) {
        for (int i = 0; i < cardList.size(); i++) {
            ImageView imageView1 = findViewById(cardList.get(i).getImgId());
            imageView1.setClickable(type);
        }
    }

}