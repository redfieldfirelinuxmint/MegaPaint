package sample;

import ActionHowever.ActionHowever;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Controller {

    @FXML private Label titulo;

    @FXML private Slider tamanio;

    @FXML private Button cuadrado;

    @FXML private Button linea;

    @FXML private Button circulo;

    @FXML private Button borrar;

    @FXML private Button pincel;

    @FXML private ColorPicker colorPicker;

    @FXML private ComboBox<String> comboBox;

    @FXML private Canvas lienzo;

    private GraphicsContext context;

    boolean pincelazo = true, boradorzaso = false, cuadraso = false , ovalo = false, lineazo = false, oneClick = false;

    double inicioX, inicioY , finX, finY;

    @FXML void initialize(){

        context = lienzo.getGraphicsContext2D();
        colorPicker.setValue(Color.BLACK);
        context.setFill(colorPicker.getValue());
        pincel.setOpacity(0.5);

        comboBox.getItems().addAll("Lineas","Ajedrez","Estrella","Estrella Double");

        tamanio.valueProperty().addListener((observable, oldValue, newValue) -> pintar(newValue.intValue()));

        tamanio.setMin(0);

        System.out.println(lienzo.getWidth());

    }

    @FXML void pintar(int valor){

        context.setFill(Color.WHITESMOKE);
        context.fillRect(0,0,lienzo.getWidth(),lienzo.getHeight());
        context.setFill(colorPicker.getValue());
        titulo.setText(valor + "");

        switch (comboBox.getSelectionModel().getSelectedIndex()){

            case 0:

                double diviciones = lienzo.getHeight() / valor;

                for(int x = 0; x < valor + 1; x++){

                    context.strokeLine(0,diviciones*x,lienzo.getWidth(),diviciones*x);
                    context.strokeLine(diviciones*x,0,diviciones*x,lienzo.getHeight());

                }

                break;
            case 1:

                double diviciones2 =  lienzo.getHeight() / valor;
                double pos = 0;
                int cont = 0;

                boolean f = true;

                    for(int k = 0; k < valor ; k++){

                        if(f) context.setFill(Color.WHITE);
                        else context.setFill(Color.BLACK);

                        f = !f;

                        context.fillRect(k*diviciones2,pos,(k*diviciones2)+diviciones2, pos+diviciones2);

                        cont++;

                        if(k == valor - 1){
                            if(cont != valor*valor) {
                                if (valor % 2 == 0) f = !f;
                                pos += diviciones2;
                                k = -1;
                            }
                            else{
                                break;
                            }
                        }

                    }

                break;
            case 2:

                double divicionAlto = lienzo.getHeight() / 2;
                double divicionAncho = lienzo.getWidth() / 2;
                double divicion = divicionAlto / valor;

                context.strokeLine(0,divicionAlto,lienzo.getWidth(),divicionAlto);
                context.strokeLine(divicionAncho,0,divicionAncho,lienzo.getHeight());

                for(int l = 1; l<valor+2; l++){

                    context.strokeLine(divicionAncho,(l*divicion),divicionAncho+(l*divicion),divicionAlto);
                    context.strokeLine(divicionAncho,(l*divicion),divicionAncho-(l*divicion),divicionAlto);
                    context.strokeLine(divicionAncho, divicionAlto + (l*divicion),(l*divicion),divicionAlto);
                    context.strokeLine(divicionAncho, divicionAlto + (l*divicion),lienzo.getWidth()-(l*divicion),divicionAlto);

                }

                break;
            case 3:
                double divicionAlto2 = lienzo.getHeight() / 2;
                double divicionAncho2 = lienzo.getWidth() / 2;
                double divicion2 = divicionAncho2 / valor;
                double inicioAqui = Math.sqrt(Math.pow(lienzo.getWidth()/2,2) + Math.pow(lienzo.getHeight()/2,2));
                double inicioReal = 90;
                double divicionPartes = (((inicioAqui-inicioReal)/valor)*55)/90;

                for(int l = 1; l<valor+2; l++){

                    context.strokeLine(l*divicion2,0,0,divicionAlto2 - (l*divicion2));
                    context.strokeLine(lienzo.getWidth() - (l*divicion2),0,lienzo.getWidth(),divicionAlto2-(l*divicion2));
                    context.strokeLine((l*divicion2),lienzo.getHeight(),0,divicionAlto2+(l*divicion2));
                    context.strokeLine(lienzo.getWidth()-(l*divicion2),lienzo.getHeight(),lienzo.getWidth(),divicionAlto2+(l*divicion2));

                    context.strokeLine(divicionAncho2,(l*divicion2),divicionAncho2+(l*divicion2),divicionAlto2);
                    context.strokeLine(divicionAncho2,(l*divicion2),divicionAncho2-(l*divicion2),divicionAlto2);
                    context.strokeLine(divicionAncho2, divicionAlto2 + (l*divicion2),(l*divicion2),divicionAlto2);
                    context.strokeLine(divicionAncho2, divicionAlto2 + (l*divicion2),lienzo.getWidth()-(l*divicion2),divicionAlto2);

                    context.strokeLine(inicioReal + (l*divicionPartes),inicioReal + (l*divicionPartes),divicionAncho2 + (l*divicionPartes), divicionAlto2 - (l*divicionPartes));
                    context.strokeLine(inicioReal + (l*divicionPartes),inicioReal + (l*divicionPartes),divicionAncho2 - (l*divicionPartes), divicionAlto2 + (l*divicionPartes));
                    context.strokeLine(divicionAncho2 + (l*divicionPartes),divicionAlto2 + (l*divicionPartes),lienzo.getWidth() - inicioReal - (l*divicionPartes), inicioReal + (l*divicionPartes));
                    context.strokeLine(divicionAncho2 + (l*divicionPartes),divicionAlto2 + (l*divicionPartes),inicioReal + (l*divicionPartes), lienzo.getHeight() - inicioReal - (l*divicionPartes));

                }
                                break;

        }//switch


    }

    @FXML void arrastrar(MouseEvent event) {
        if(boradorzaso || pincelazo) context.fillOval(event.getX() - (tamanio.getValue()/2),event.getY() - (tamanio.getValue()/2),tamanio.getValue(),tamanio.getValue());
    } //arrastrar

    @FXML void clickFigura(MouseEvent event){

        if (cuadraso){

            if(oneClick){

                finX = event.getX();
                finY = event.getY();
                finX = finX - inicioX;
                finY = finY - inicioY;

                if(finX < 0 && finY < 0 ) context.fillRect(event.getX(),event.getY(),Math.abs(finX),Math.abs(finY));
                else if(finX < 0) context.fillRect(event.getX(),inicioY,Math.abs(finX),Math.abs(finY));
                else if(finY < 0) context.fillRect(inicioX,event.getY(),Math.abs(finX),Math.abs(finY));
                else context.fillRect(inicioX,inicioY,finX,finY);

                System.out.println("fin");
                System.out.println(finX);
                System.out.println(finY);

            }
            else {

                inicioX = event.getX();
                inicioY = event.getY();
                System.out.println("inicio");
                System.out.println(inicioX);
                System.out.println(inicioY);

            }

        }//if mayor
        else if(lineazo){

            if(oneClick){

                finX = event.getX();
                finY = event.getY();

                context.strokeLine(inicioX,inicioY,finX,finY);

            }
            else{
                inicioX = event.getX();
                inicioY = event.getY();
            }

        }// if mayor2
        else if (ovalo){

            if(oneClick){

                finX = event.getX();
                finY = event.getY();
                finX = finX - inicioX;
                finY = finY - inicioY;

                if(finX < 0 && finY < 0 ) context.fillOval(event.getX(),event.getY(),Math.abs(finX),Math.abs(finY));
                else if(finX < 0) context.fillOval(event.getX(),inicioY,Math.abs(finX),Math.abs(finY));
                else if(finY < 0) context.fillOval(inicioX,event.getY(),Math.abs(finX),Math.abs(finY));
                else context.fillOval(inicioX,inicioY,finX,finY);

                System.out.println("fin");
                System.out.println(finX);
                System.out.println(finY);

            }
            else {

                inicioX = event.getX();
                inicioY = event.getY();
                System.out.println("inicio");
                System.out.println(inicioX);
                System.out.println(inicioY);

            }

        }//if mayor3

        oneClick = !oneClick;

    }//click figura

    @FXML void cambiarABorrar(MouseEvent event) {

        if(event.getClickCount() == 2){
            context.setFill(Color.WHITESMOKE);
            context.fillRect(0,0,lienzo.getWidth(),lienzo.getHeight());
            context.setFill(colorPicker.getValue());
        }
        else{
            colorPicker.setValue(Color.WHITESMOKE);
            context.setFill(colorPicker.getValue());
            tamanio.setValue(50);
        }

        boradorzaso = !boradorzaso;
        if(boradorzaso){
            todoOpacity();
            cuadraso = false;
            lineazo = false;
            pincelazo = false;
            ovalo = false;
            borrar.setOpacity(0.5);
        }
        else borrar.setOpacity(1);

    } //cambiarABorrar

    @FXML void cambiarACirculo(ActionEvent event) {

        ovalo = !ovalo;
        if(ovalo){
            todoOpacity();
            cuadraso = false;
            lineazo = false;
            pincelazo = false;
            boradorzaso = false;
            circulo.setOpacity(0.5);
            oneClick = false;
        }
        else circulo.setOpacity(1);

    }

    @FXML void cambiarACuadrado(ActionEvent event) {

        cuadraso = !cuadraso;
        if(cuadraso){
            todoOpacity();
            ovalo = false;
            lineazo = false;
            pincelazo = false;
            boradorzaso = false;
            oneClick = false;
            cuadrado.setOpacity(0.5);
        }
        else cuadrado.setOpacity(1);

    }

    @FXML void cambiarALinea(ActionEvent event) {

        lineazo = !lineazo;
        if(lineazo){
            todoOpacity();
            cuadraso = false;
            ovalo = false;
            pincelazo = false;
            boradorzaso = false;
            oneClick = false;
            linea.setOpacity(0.5);
        }
        else linea.setOpacity(1);

    }

    @FXML void cambiarAPincel(ActionEvent event) {

        pincelazo = !pincelazo;
        if(pincelazo){
            todoOpacity();
            cuadraso = false;
            lineazo = false;
            ovalo = false;
            boradorzaso = false;
            oneClick = false;
            pincel.setOpacity(0.5);
        }
        else pincel.setOpacity(1);

    }

    @FXML void cambiarColor(ActionEvent event) {
        context.setFill(colorPicker.getValue());
    } //cambiar color

    @FXML void cambiarTamanio(MouseEvent event) {

    }

    @FXML void todoOpacity(){
        borrar.setOpacity(1);
        pincel.setOpacity(1);
        circulo.setOpacity(1);
        linea.setOpacity(1);
        cuadrado.setOpacity(1);
    }

    @FXML void enteredAndExited(Event event){

        if(((Button)event.getSource()).getOpacity() != 0.5 && event.getEventType().equals("MOUSE_EXITED")){
            new ActionHowever(event,0.5);
        }

    }

}
