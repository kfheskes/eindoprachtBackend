package nl.backend.eindoprdracht.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ControllerHelper {

//TODO krijg geen foutmelding bij dubbelen punt
    public static String checkForBindingResult (BindingResult br) {
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : br.getFieldErrors()) {
            sb.append(fe.getField());
            sb.append(" : ");
            sb.append(fe.getDefaultMessage());
            sb.append("\n");
            sb.append(fe.getCode());
            sb.append("Object: ").append(br.getObjectName()).append("\n");
        }
        return sb.toString();
    }
}


//TODO: check volgende optie voor meerwaard:

//Verbeter de Leesbaarheid: Overweeg een betere formattering voor de uitvoer, zoals het gebruik van tabs of het indelen van de uitvoer in een tabelvorm, wat kan helpen bij het lezen en begrijpen van de fouten.
//
//java
//Copy code
//sb.append(String.format("%-20s : %s\n", fe.getField(), fe.getDefaultMessage()));