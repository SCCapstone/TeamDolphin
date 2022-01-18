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
}