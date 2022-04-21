package com.example.teamdolphin

import org.junit.Assert.*
import org.junit.Test

class FileCreationTest{

    /*
    For Empty String
    Should return false
     */
    @Test
    fun inputIsNotValid(){
        val name = ""
        val result = FileCreation.projectNameIsValid(name)
        assertEquals(false, result)
    }
    /*
    For non-empty String
    Should return true
     */
    @Test
    fun inputIsValid(){
        val name = "some name"
        val result = FileCreation.projectNameIsValid(name)
        assertEquals(true, result)
    }

    /*
    Test width as input from the user if given
     */
    @Test
    fun isWidthValid(){
        val width = 100
        val result = FileCreation.isProjectWidthValid(width)
        assertEquals(true, result)
    }

    /*
    Test if width input is invalid from the user if given
     */
    @Test
    fun isWidthNotValid(){
        val width = 0
        val result = FileCreation.isProjectWidthValid(width)
        assertEquals(false, result)
    }
    /*
    Test height as input from the user if given
     */
    @Test
    fun isHeightValid(){
        val height = 100
        val result = FileCreation.isProjectHeightValid(height)
        assertEquals(true, result)
    }

    /*
    Test if height input is invalid from the user if given
     */
    @Test
    fun isHeightNotValid(){
        val height = 0
        val result = FileCreation.isProjectHeightValid(height)
        assertEquals(false, result)
    }

}