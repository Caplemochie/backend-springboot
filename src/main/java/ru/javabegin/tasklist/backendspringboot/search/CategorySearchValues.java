package ru.javabegin.tasklist.backendspringboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by tagir on 06.10.2020.
 */

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor

// возможные значения, по которым можно искать категории
public class CategorySearchValues {

    private String text;
}
