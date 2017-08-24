package com.lsb.myapplicationw2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class Calculation extends AppCompatActivity {

    private TextView mTitleText;
    private TextView mPeopleNum;
    private TextView mMaxmoneyText;
    private TextView mMoneyText;

    private LinearLayout m0CalculAddNameLay;
    private String[] m1CalculAddName;
    private String[] m2CalculAddMoney;
    private Boolean[] m4CalculAddCheck;
    private Boolean[] m5CalculAddPay;

    private int checkedMoney;  // 고정 금액이 총 금액을 넘는지 체크
    private int checkedCount;
    private RadioButton mRadioButton0;
    private RadioButton mRadioButton100;
    private RadioButton mRadioButton10;
    private RadioButton mRadioButton1000;
    private String[] m3CalculAddMoney;
    private int division;


    // 새로 만들어 추가된 에디트박스들의 평균 돈

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mTitleText = (TextView) findViewById(R.id.calcul_title_text);
        mPeopleNum = (TextView) findViewById(R.id.calcul_peoplenum_text);
        mMaxmoneyText = (TextView) findViewById(R.id.calcul_maxmoney_text);
        mMoneyText = (TextView) findViewById(R.id.calcul_money_text);


        mRadioButton0 = (RadioButton) findViewById(R.id.radio_button_c0);
        mRadioButton10 = (RadioButton) findViewById(R.id.radio_button_c10);
        mRadioButton100 = (RadioButton) findViewById(R.id.radio_button_c100);
        mRadioButton1000 = (RadioButton) findViewById(R.id.radio_button_c1000);


        checkedCount = 0;


        m0CalculAddNameLay = (LinearLayout) findViewById(R.id.add_all_lay);


//        m1CalculAddNameLay = (LinearLayout) findViewById(R.id.add_lay_calcul_name);
//        m2CalculAddMoneyLay = (LinearLayout) findViewById(R.id.add_lay_calcul_money);
//        m3CalculAddCheckLay = (LinearLayout) findViewById(R.id.add_lay_calcul_check);
//        m4CalculAddPayLay = (LinearLayout) findViewById(R.id.add_lay_calcul_pay);


        if (getIntent() != null) {

            DecimalFormat df = new DecimalFormat("###,###.####");
            String result = "";


            String title = getIntent().getStringExtra("title");
            String people_num = getIntent().getStringExtra("people");
            String money = getIntent().getStringExtra("money");

            division = getIntent().getIntExtra("division", 0);

            if (division == 1) {
                mRadioButton0.setChecked(true);
            } else if (division == 10) {
                mRadioButton10.setChecked(true);
            } else if (division == 100) {
                mRadioButton100.setChecked(true);
            } else if (division == 1000) {
                mRadioButton1000.setChecked(true);
            }

            int people_money0 = (Integer.valueOf(money) / Integer.valueOf(people_num));
            int people_money = (int) ((double) people_money0 - (people_money0 % division));

            int division_people_money = (int) ((double) (people_money0 % division));


            mTitleText.setText(title);
            mPeopleNum.setText(people_num);

            mMaxmoneyText.setText(money);
            result = df.format(Long.parseLong(mMaxmoneyText.getText().toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
            mMaxmoneyText.setText(result);    // 결과 텍스트 셋팅.


            // 이름 받는 배열 생성
            m1CalculAddName = new String[Integer.valueOf(mPeopleNum.getText().toString())];

            m2CalculAddMoney = new String[Integer.valueOf(mPeopleNum.getText().toString())];
            m3CalculAddMoney = new String[Integer.valueOf(mPeopleNum.getText().toString())];
            m4CalculAddCheck = new Boolean[Integer.valueOf(mPeopleNum.getText().toString())];
            m5CalculAddPay = new Boolean[Integer.valueOf(mPeopleNum.getText().toString())];


            for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString()); i++) {
                String n = getIntent().getStringExtra("name2" + i);
                m1CalculAddName[i] = n;
            }
            // 이름 받는 배열 생성


            mMoneyText.setText(String.valueOf(people_money));
            result = df.format(Long.parseLong(mMoneyText.getText().toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
            mMoneyText.setText(result);    // 결과 텍스트 셋팅.


            // 레이아웃 생성
            for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString()); i++) {
                LinearLayout parentLL = new LinearLayout(Calculation.this);
                parentLL.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                parentLL.setTag("AllLay" + i);
                parentLL.setId(i);
                parentLL.setOrientation(LinearLayout.HORIZONTAL);
                m0CalculAddNameLay.addView(parentLL);


            }


//                 이름 받아서 에디트 박스 수 생성
            for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString()); i++) {
                LinearLayout Alllay = (LinearLayout) m0CalculAddNameLay.findViewWithTag("AllLay" + i);


                final EditText nameEdit = new EditText(Calculation.this);

//                nameEdit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                Lp.weight = 1;
                nameEdit.setPadding(20, 10, 10, 10);
                nameEdit.setTextSize(24);
                nameEdit.setHint((i + 1) + ". " + "이름");
                nameEdit.setId(i);
                nameEdit.setTag("NameView" + i);
                nameEdit.requestFocus();
                nameEdit.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                nameEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                nameEdit.setText(m1CalculAddName[i]);


                nameEdit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().isEmpty()) {
                            m1CalculAddName[nameEdit.getId()] = s.toString();
                        } else {
                            m1CalculAddName[nameEdit.getId()] = s.toString();
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                Alllay.addView(nameEdit, Lp);
            }
            // 이름 받아서 에디트 박스 수 생성

            // 평균 금액
            for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString()); i++) {
                LinearLayout Alllay = (LinearLayout) m0CalculAddNameLay.findViewWithTag("AllLay" + i);


                final EditText mPeopleMoneyText = new EditText(Calculation.this);
//                final EditNumber mPeopleMoneyText = new EditNumber(Calculation.this);


                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                Lp.weight = 1;
                mPeopleMoneyText.setPadding(20, 10, 10, 10);
                mPeopleMoneyText.setTextSize(24);
                mPeopleMoneyText.setHint((i + 1) + ". " + "금액");
                mPeopleMoneyText.setId(i);
                mPeopleMoneyText.setTag("MoneyView" + i);

                mPeopleMoneyText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_NORMAL);
                mPeopleMoneyText.setText(mMoneyText.getText().toString());
//                .replace(",", "")
                m2CalculAddMoney[i] = mMoneyText.getText().toString().replace(",", "");
                mPeopleMoneyText.addTextChangedListener(new TextWatcher() {


                    String strAmount = "";

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (s.toString().isEmpty()) {
                            mPeopleMoneyText.setText("0");
                        } else if (!s.toString().equals(strAmount)) { // StackOverflow 방지
                            strAmount = makeStringComma(s.toString().replace(",", ""));

                            mPeopleMoneyText.setText(strAmount);
                            Editable e = mPeopleMoneyText.getText();
                            Selection.setSelection(e, strAmount.length());
                            if (Integer.valueOf(s.toString().replace(",", "")) > Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""))) {
                                mPeopleMoneyText.setText(mMoneyText.getText().toString().replace(",", ""));
                                Toast.makeText(Calculation.this, "총액 보다 금액이 많습니다.", Toast.LENGTH_SHORT).show();
                            } else if (Integer.valueOf(s.toString().replace(",", "")) < 0) {
                                mPeopleMoneyText.setText(mMoneyText.getText().toString().replace(",", ""));
                                Toast.makeText(Calculation.this, "금액이 적습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                m2CalculAddMoney[mPeopleMoneyText.getId()] = s.toString().replace(",", "");
                            }


                        }

                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                    protected String makeStringComma(String str) {    // 천단위 콤마 처리
                        if (str.length() == 0)
                            return "";
                        long value = Long.parseLong(str);
                        DecimalFormat format = new DecimalFormat("###,###");
                        return format.format(value);
                    }
                });
                Alllay.addView(mPeopleMoneyText, Lp);
            }


            // 평균 금액

//ddddddddddddddddddddddddd  절사 테스트ㅡ으으으으으으

            for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString()); i++) {
                LinearLayout Alllay = (LinearLayout) m0CalculAddNameLay.findViewWithTag("AllLay" + i);


                final TextView mDismoneyText = new TextView(Calculation.this);
//                final EditNumber mPeopleMoneyText = new EditNumber(Calculation.this);


                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                mDismoneyText.setTextSize(15);

                mDismoneyText.setId(i);
                mDismoneyText.setTag("Division" + i);

                mDismoneyText.setText("(" + division_people_money + ")");
//                .replace(",", "")


                Alllay.addView(mDismoneyText, Lp);
            }


            ///  절사 테스트트으으으으으으으으으으으으으으으으으르으으르르르르르르르르르르를르르르르르르르르르를르르르를르르를

            // 고정금액 체크 박스

            for (
                    int i = 0; i < Integer.valueOf(mPeopleNum.getText().

                    toString()); i++)

            {
                LinearLayout Alllay = (LinearLayout) m0CalculAddNameLay.findViewWithTag("AllLay" + i);
                final CheckBox checkedMoney = new CheckBox(Calculation.this);
//                checkedMoney.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                Lp.weight = 1;
                checkedMoney.setTextSize(14);


                checkedMoney.setId(i);
                checkedMoney.setTag("CheckView" + i);
                checkedMoney.setText("고정");
                m4CalculAddCheck[i] = false;
                checkedMoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            m4CalculAddCheck[checkedMoney.getId()] = true;
                        } else {
                            m4CalculAddCheck[checkedMoney.getId()] = false;
                        }

                    }
                });
                Alllay.addView(checkedMoney, Lp);
            }

            // 고정금액 체크 박스


            for (
                    int i = 0; i < Integer.valueOf(mPeopleNum.getText().

                    toString()); i++)

            {
                LinearLayout Alllay = (LinearLayout) m0CalculAddNameLay.findViewWithTag("AllLay" + i);
                final CheckBox checkedPay = new CheckBox(Calculation.this);
//                checkedPay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                LinearLayout.LayoutParams Lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                Lp.weight = 1;
                checkedPay.setTextSize(14);
                checkedPay.setId(i);
                checkedPay.setTag("PayView" + i);
                checkedPay.setText("결제자");
                m5CalculAddPay[i] = false;
                checkedPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            if (checkedCount == 0) {
                                m5CalculAddPay[checkedPay.getId()] = true;
                                checkedCount = 1;
                            } else {
                                checkedPay.setChecked(false);
                                Toast.makeText(Calculation.this, "결제자는 한명 이어야 합니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (m5CalculAddPay[checkedPay.getId()] == true) {
                                checkedCount = 0;
                                m5CalculAddPay[checkedPay.getId()] = false;
                            }
                        }
                    }
                });
                Alllay.addView(checkedPay, Lp);
            }


        }


    }


    public void onClickButton(View view) {


        int people_num = Integer.valueOf(mPeopleNum.getText().toString()); // 사람 수 만큼 for문을 위해

        if (mRadioButton0.isChecked()) {
            division = 1;
        } else if (mRadioButton10.isChecked()) {
            division = 10;
        } else if (mRadioButton100.isChecked()) {
            division = 100;
        } else if (mRadioButton1000.isChecked()) {
            division = 1000;
        }


        switch (view.getId()) {

            case R.id.button_calc:


                //고정금액 - 총금액을 위해
                int minusMoney = Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""));
                // 한 사람당 돌아갈 동
                int newPeopleMoney = 0;
                //체크한 돈 총액 비교를 위해
                checkedMoney = 0;

                // 체크한 사람 숫자
                int checkedMoneyPeopleCount = 0;

                for (int i = 0; i < people_num; i++) {
                    if (m4CalculAddCheck[i]) {
                        checkedMoney = checkedMoney + Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        checkedMoneyPeopleCount++;
                    }
                }
                if (checkedMoneyPeopleCount == Integer.valueOf(mPeopleNum.getText().toString())) {

                    Toast.makeText(this, "고정 금액이 전부 선택되어" + "\n" + "  계산할 수 없습니다." +
                            "\n" + "  다시 설정 해 주세요", Toast.LENGTH_SHORT).show();

                } else if (checkedMoney > Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""))) {
                    Toast.makeText(this, "고정 금액의 총합이 " + "\n" + "총 금액을 초과 하였습니다." +
                            "\n" + "다시 설정 해 주세요", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")); i++) {
                        if (m4CalculAddCheck[i]) {
                            minusMoney = minusMoney - Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        }
                    }


                    if (minusMoney > (Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")))) {
                        int newPeopleMoney0 = minusMoney / (Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")) - checkedMoneyPeopleCount);

                        newPeopleMoney = (int) (newPeopleMoney0 - ((double) (newPeopleMoney0 % division)));

                        int new_division_people_money = (int) ((double) (newPeopleMoney0 % division));

                        for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")); i++) {
                            if (!m4CalculAddCheck[i]) {
                                LinearLayout vvv = (LinearLayout) findViewById(i);
                                EditText PeopleMoney = (EditText) vvv.findViewWithTag("MoneyView" + i);
                                TextView divisionMoney = (TextView) vvv.findViewWithTag("Division" + i);
                                PeopleMoney.setText("" + newPeopleMoney);
                                m2CalculAddMoney[i] = PeopleMoney.getText().toString();
                                divisionMoney.setText("(" + new_division_people_money + ")");
                                m3CalculAddMoney[i] = divisionMoney.getText().toString();
//                            EditText PeopleMoney = (EditText) PeopleMoneyText.findViewWithTag("MoneyView" + i);
//                            PeopleMoney.setText(""+newPeopleMoney);
                            } else {

                                LinearLayout vvv = (LinearLayout) findViewById(i);
                                TextView divisionMoney = (TextView) vvv.findViewWithTag("Division" + i);
                                divisionMoney.setText("(" + 0 + ")");
                            }
                        }
                    }


                }
                break;

            case R.id.button_ok:

                //고정금액 - 총금액을 위해
                int minusMoney_2 = Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""));
                // 한 사람당 돌아갈 동
                int newPeopleMoney_2 = 0;
                //체크한 돈 총액 비교를 위해
                checkedMoney = 0;

                // 체크한 사람 숫자
                int checkedMoneyPeopleCount_2 = 0;

                for (int i = 0; i < people_num; i++) {
                    if (m4CalculAddCheck[i]) {
                        checkedMoney = checkedMoney + Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        checkedMoneyPeopleCount_2++;
                    }
                }
                if (checkedMoneyPeopleCount_2 == Integer.valueOf(mPeopleNum.getText().toString())) {

                    Toast.makeText(this, "고정 금액이 전부 선택되어" + "\n" + "  계산할 수 없습니다." +
                            "\n" + "  다시 설정 해 주세요", Toast.LENGTH_SHORT).show();

                } else if (checkedMoney > Integer.valueOf(mMaxmoneyText.getText().toString().replace(",", ""))) {
                    Toast.makeText(this, "고정 금액의 총합이 " + "\n" + "총 금액을 초과 하였습니다." +
                            "\n" + "다시 설정 해 주세요", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")); i++) {
                        if (m4CalculAddCheck[i]) {
                            minusMoney_2 = minusMoney_2 - Integer.valueOf(m2CalculAddMoney[i].replace(",", ""));
                        }
                    }


                    if (minusMoney_2 > (Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")))) {
                        int newPeopleMoney0 = minusMoney_2 / (Integer.valueOf(mPeopleNum.getText().toString().replace(",", ""))
                                - checkedMoneyPeopleCount_2);

                        newPeopleMoney = (int) (newPeopleMoney0 - ((double) (newPeopleMoney0 % division)));

                        int new_division_people_money = (int) ((double) (newPeopleMoney0 % division));

                        for (int i = 0; i < Integer.valueOf(mPeopleNum.getText().toString().replace(",", "")); i++) {
                            if (!m4CalculAddCheck[i]) {
                                LinearLayout vvv = (LinearLayout) findViewById(i);
                                EditText PeopleMoney = (EditText) vvv.findViewWithTag("MoneyView" + i);
                                TextView divisionMoney = (TextView) vvv.findViewWithTag("Division" + i);
                                PeopleMoney.setText("" + newPeopleMoney);
                                m2CalculAddMoney[i] = PeopleMoney.getText().toString();
                                divisionMoney.setText("(" + new_division_people_money + ")");
                                m3CalculAddMoney[i] = divisionMoney.getText().toString();
//                            EditText PeopleMoney = (EditText) PeopleMoneyText.findViewWithTag("MoneyView" + i);
//                            PeopleMoney.setText(""+newPeopleMoney);
                            } else {

                                LinearLayout vvv = (LinearLayout) findViewById(i);
                                TextView divisionMoney = (TextView) vvv.findViewWithTag("Division" + i);
                                divisionMoney.setText("(" + 0 + ")");
                            }
                        }


                    }

                    int calcPeopleCheck = 32;
                    String calcPeopleName;
                    Intent intent = new Intent(Calculation.this, SendActivity.class);
                    intent.putExtra("paypcheck", "NO");
                    for (int i = 0; i < people_num; i++) {

                        if (m5CalculAddPay[i] == true) {

                            calcPeopleCheck = i;

                            LinearLayout vvv = (LinearLayout) findViewById(i);
                            EditText PeopleName = (EditText) vvv.findViewWithTag("NameView" + i);
                            calcPeopleName = PeopleName.getText().toString();
                            intent.putExtra("paypeople", calcPeopleName);
                            intent.putExtra("paypcheck", "OK");

                        } else {

                        }
                    }


                    for (int i = 0; i < people_num; i++) {

                        if (i != calcPeopleCheck) {
                            LinearLayout vvv = (LinearLayout) findViewById(i);
                            EditText PeopleName = (EditText) vvv.findViewWithTag("NameView" + i);
                            String N = PeopleName.getText().toString();

                            EditText PeopleMoney = (EditText) vvv.findViewWithTag("MoneyView" + i);
                            String M = PeopleMoney.getText().toString();

                            TextView divisionMoney = (TextView) vvv.findViewWithTag("Division" + i);
                            String D = divisionMoney.getText().toString();
                            intent.putExtra("name" + i, N);
                            intent.putExtra("Money" + i, M);
                            intent.putExtra("division" + i, D);

                        } else {

                        }

                    }
                    intent.putExtra("MaxMoney", mMaxmoneyText.getText().toString());
                    intent.putExtra("num", people_num);
                    intent.putExtra("title", mTitleText.getText().toString());
                    intent.putExtra("checkPeople", calcPeopleCheck);
                    startActivity(intent);




                }

                //확인만 누를 때를 위해 한번더 입력 나중에 해결하자.



        }

    }
}
