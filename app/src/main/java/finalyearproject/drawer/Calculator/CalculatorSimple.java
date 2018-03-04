package finalyearproject.drawer.Calculator;

/**
 * Created by Dvaid on 02/04/2015.
 */
import android.os.Bundle; import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu; import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; import android.widget.EditText;

import finalyearproject.drawer.R;

public class CalculatorSimple extends Fragment implements View.OnClickListener {
    Button one, two, three, four, five, six, seven, eight, nine, zero, add, sub, mul, div, cancel, equal;
    EditText disp;
    int op1;
    int op2;
    String optr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View android = inflater.inflate(R.layout.frag_calculator_simple, container, false);
        one = (Button) android.findViewById(R.id.one);
        two = (Button) android.findViewById(R.id.two);
        three = (Button) android.findViewById(R.id.three);
        four = (Button) android.findViewById(R.id.four);
        five = (Button) android.findViewById(R.id.five);
        six = (Button) android.findViewById(R.id.six);
        seven = (Button) android.findViewById(R.id.seven);
        eight = (Button) android.findViewById(R.id.eight);
        nine = (Button) android.findViewById(R.id.nine);
        zero = (Button) android.findViewById(R.id.zero);
        add = (Button) android.findViewById(R.id.add);
        sub = (Button) android.findViewById(R.id.sub);
        mul = (Button) android.findViewById(R.id.mul);
        div = (Button) android.findViewById(R.id.div);
        cancel = (Button) android.findViewById(R.id.cancel);
        equal = (Button) android.findViewById(R.id.equal);
        disp = (EditText) android.findViewById(R.id.display);

        try {
            one.setOnClickListener(this);
            two.setOnClickListener(this);
            three.setOnClickListener(this);
            four.setOnClickListener(this);
            five.setOnClickListener(this);
            six.setOnClickListener(this);
            seven.setOnClickListener(this);
            eight.setOnClickListener(this);
            nine.setOnClickListener(this);
            zero.setOnClickListener(this);
            cancel.setOnClickListener(this);
            add.setOnClickListener(this);
            sub.setOnClickListener(this);
            mul.setOnClickListener(this);
            div.setOnClickListener(this);
            equal.setOnClickListener(this);
        } catch (Exception e) {

        }

        return android;
    }


    public void operation() {
        if (optr.equals("+")) {
            op2 = Integer.parseInt(disp.getText().toString());
            disp.setText("");
            op1 = op1 + op2;
            disp.setText(Integer.toString(op1));

        } else if (optr.equals("-")) {
            op2 = Integer.parseInt(disp.getText().toString());
            disp.setText("");
            op1 = op1 - op2;
            disp.setText( Integer.toString(op1));
        } else if (optr.equals("*")) {
            op2 = Integer.parseInt(disp.getText().toString());
            disp.setText("");
            op1 = op1 * op2;
            disp.setText(Integer.toString(op1));
        } else if (optr.equals("/")) {
            op2 = Integer.parseInt(disp.getText().toString());
            disp.setText("");
            op1 = op1 / op2;
            disp.setText( Integer.toString(op1));
        }
    }

    @Override
    public void onClick(View arg0) {
        Editable str = disp.getText();
        switch (arg0.getId()) {
            case R.id.one:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(two.getText());
                disp.setText(str);

                break;

            case R.id.two:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(two.getText());
                disp.setText(str);
                break;

            case R.id.three:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(three.getText());
                disp.setText(str);
                break;

            case R.id.four:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(four.getText());
                disp.setText(str);

                break;

            case R.id.five:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(five.getText());
                disp.setText(str);

                break;

            case R.id.six:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(six.getText());
                disp.setText(str);
                break;

            case R.id.seven:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(eight.getText());
                disp.setText(str);
                break;

            case R.id.eight:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(nine.getText());
                disp.setText(str);
                break;

            case R.id.nine:
                if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                }
                str = str.append(zero.getText());
                disp.setText(str);
                break;

            case R.id.cancel:
                op1 = 0;
                op2 = 0;
                disp.setText("");
                disp.setHint("Perform Operation :)");
                break;

            case R.id.add:
                optr = "+";
                if (op1 == 0) {
                    op1 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                } else if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                } else {
                    op2 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                    op1 = op1 + op2;
                    disp.setText(Integer.toString(op1));
                }
                break;

            case R.id.sub:
                optr = "-";
                if (op1 == 0) {
                    op1 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                } else if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                } else {
                    op2 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                    op1 = op1 - op2;
                    disp.setText(Integer.toString(op1));
                }
                break;

            case R.id.mul:
                optr = "*";
                if (op1 == 0) {
                    op1 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                } else if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                } else {
                    op2 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                    op1 = op1 * op2;
                    disp.setText(Integer.toString(op1));
                }
                break;

            case R.id.div:
                optr = "/";
                if (op1 == 0) {
                    op1 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                } else if (op2 != 0) {
                    op2 = 0;
                    disp.setText("");
                } else {
                    op2 = Integer.parseInt(disp.getText().toString());
                    disp.setText("");
                    op1 = op1 / op2;
                    disp.setText(Integer.toString(op1));
                }
                break;

            case R.id.equal:
                if (!optr.equals(null)) {
                    if (op2 != 0) {
                        if (optr.equals("+")) {
                            disp.setText(""); /*op1 = op1 + op2;*/
                            disp.setText(Integer.toString(op1));
                        } else if (optr.equals("-")) {
                            disp.setText("");/* op1 = op1 - op2;*/
                            disp.setText(Integer.toString(op1));
                        } else if (optr.equals("*")) {
                            disp.setText("");/* op1 = op1 * op2;*/
                            disp.setText(Integer.toString(op1));
                        } else if (optr.equals("/")) {
                            disp.setText("");/* op1 = op1 / op2;*/
                            disp.setText(Integer.toString(op1));
                        }

                    } else {
                        operation();
                    }
                }
                break;
        }
    }



}


