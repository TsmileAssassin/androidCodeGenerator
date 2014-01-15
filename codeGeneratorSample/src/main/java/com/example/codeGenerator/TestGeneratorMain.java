package com.example.codeGenerator;

import com.tsmile.codeGenerator.Generator;

public class TestGeneratorMain {
    public static void main(String[] args) throws Exception {
        Generator generator = new Generator();
        generator.generateActivity(System.getProperty("user.dir") + "//res//layout//act_test.xml", "TestActivity",
                "com.example.codeGenerator");

        Generator generator2 = new Generator();
        generator2.generateFindViewCode(System.getProperty("user.dir") + "//res//layout//act_test.xml", "TestFindView");
    }
}
