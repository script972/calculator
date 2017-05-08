package com.example.script972.calculator;

import java.util.EnumMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.script972.calculator.OperationType;
import com.example.script972.calculator.Operation;
import com.example.script972.calculator.R;
import com.example.script972.calculator.Store;

public class MainActivity extends AppCompatActivity {

    //component activity
    //компоненты активити
    private EditText txtResult;

    private Button btnAdd;
    private Button btnDivide;
    private Button btnMultiply;
    private Button btnSubtract;

    //Типы операции
    private OperationType operType;

    //Save here data and OperationType
    //Храню данные и тип операции
    private EnumMap<Store, Object> commands = new EnumMap<Store, Object>(Store.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialization vars
        //Инициализация переменных
        txtResult = (EditText) findViewById(R.id.txtResult);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnMultiply = (Button) findViewById(R.id.btnMultiply);
        btnSubtract = (Button) findViewById(R.id.btnSubtract);


        //add in button tag of oferation
        //Добавление в кнопки тэга операции
        btnAdd.setTag(OperationType.ADD);
        btnDivide.setTag(OperationType.DIV);
        btnMultiply.setTag(OperationType.MULT);
        btnSubtract.setTag(OperationType.MINUS);

    }

    //get data from 2 activity where more funtion
    //получаем данные с 2 активи где больше функций
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data==null){
            return;
        }
        String rrr=data.getStringExtra("value");
        txtResult.setText(rrr);
    }

    //proccess push
    //Обработчик нажатий
    public void buttonClick(View view) {

        switch (view.getId()) {
            case R.id.btnAdd:
            case R.id.btnSubtract:
            case R.id.btnDivide:
            case R.id.btnMultiply: {//for operation // для операций

                operType = (OperationType) view.getTag();//get tag operation //Получаем тэг операции

                if (!commands.containsKey(Store.OPERATION)) {//if data capacity have not operation than do... // если хранилище данных не содержит операции то выполняем

                    if (!commands.containsKey(Store.F)){
                        commands.put(Store.F, txtResult.getText());//put first number of operation //заносим первое число для операции
                    }

                    commands.put(Store.OPERATION, operType);//put operation //ложим операцию
                } else if (!commands.containsKey(Store.S)) {//else calc //в противном случаи считаем
                    commands.put(Store.S, txtResult.getText());
                    doCalc();
                    commands.put(Store.OPERATION, operType);
                    commands.remove(Store.S);
                }

                break;
            }

            case R.id.btnGeom:{//call  second activity
                Intent intent=new Intent(this, Tregeo.class);
                intent.putExtra("value",txtResult.getText().toString());
                startActivityForResult(intent,1);

                break;
            }

            case R.id.btnClear: {
                txtResult.setText("0");
                commands.clear();//clear memory //очищаем память
                break;
            }

            case R.id.btnResult: {// получаем результат

                if (commands.containsKey(Store.F) && commands.containsKey(Store.OPERATION)) {
                    commands.put(Store.S, txtResult.getText());

                    doCalc(); //просчет результат

                    commands.put(Store.OPERATION, operType);
                    commands.remove(Store.S);
                }

                break;
            }

            case R.id.btnComma: {

                if (commands.containsKey(Store.F)
                        && getDouble(txtResult.getText().toString()) == getDouble(commands
                        .get(Store.F).toString())

                        ) {

                    txtResult
                            .setText("0" + view.getContentDescription().toString());
                }

                if (!txtResult.getText().toString().contains(",")) {
                    txtResult.setText(txtResult.getText() + ",");
                }
                break;
            }

            case R.id.btnDelete: { //clear last figure from edit form //чистим последнию цифру
                txtResult.setText(txtResult.getText().delete(
                        txtResult.getText().length() - 1,
                        txtResult.getText().length()));

                if (txtResult.getText().toString().trim().length() == 0) {
                    txtResult.setText("0");
                }

                break;
            }

            default: {

                if (txtResult.getText().toString().equals("0")
                        ||

                        (commands.containsKey(Store.F) && getDouble(txtResult
                                .getText()) == getDouble(commands
                                .get(Store.F)))

                        ) {

                    txtResult.setText(view.getContentDescription().toString());
                } else {
                    txtResult.setText(txtResult.getText()
                            + view.getContentDescription().toString());
                }

            }

        }
    }

    private double getDouble(Object value) {
        double result = 0;
        try {
            result = Double.valueOf(value.toString().replace(',', '.')).doubleValue();// clear  many zero after coma //удаляем много 0 после запятой
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    private void doCalc() {

        OperationType operTypeTmp = (OperationType) commands.get(Store.OPERATION);

        // 2 + 1 +

        double result = calc(operTypeTmp,
                getDouble(commands.get(Store.F)),
                getDouble(commands.get(Store.S)));


        if (result % 1 == 0){
            txtResult.setText(String.valueOf((int)result));
        }else{
            txtResult.setText(String.valueOf(result));
        }

        commands.put(Store.F, result);

    }

    private Double calc(OperationType operType, double a, double b) {
        Operation operation=new Operation();
        switch (operType) {
            case ADD: {
                return operation.add(a, b);
            }
            case DIV: {
                return operation.dived(a, b);
            }
            case MULT: {
                return operation.multiply(a, b);
            }
            case MINUS: {
                return operation.minus(a, b);
            }
        }

        return null;
    }

}
