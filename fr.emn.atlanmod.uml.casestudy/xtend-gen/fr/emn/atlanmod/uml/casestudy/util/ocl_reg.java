package fr.emn.atlanmod.uml.casestudy.util;

import com.google.common.base.Objects;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ocl_reg {
  public static void main(final String[] args) {
    try {
      String line = null;
      FileInputStream fis = new FileInputStream("./resources/UML3.ocl");
      Charset _forName = Charset.forName("UTF-8");
      final InputStreamReader isr = new InputStreamReader(fis, _forName);
      final BufferedReader br = new BufferedReader(isr);
      FileOutputStream _fileOutputStream = new FileOutputStream("./resources/UML4.ocl");
      PrintStream _printStream = new PrintStream(_fileOutputStream);
      System.setOut(_printStream);
      while ((!Objects.equal((line = br.readLine()), null))) {
        if (((!line.startsWith("package ")) && (!line.startsWith("endpackage")))) {
          boolean _contains = line.contains("context ");
          if (_contains) {
            final String clazz = IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(line.split("::"))));
            InputOutput.<String>println(String.format("context %s", clazz));
          } else {
            InputOutput.<String>println(line);
          }
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
