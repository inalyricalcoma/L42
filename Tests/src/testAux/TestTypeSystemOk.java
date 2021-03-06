package testAux;

import helpers.TestHelper;

import static helpers.TestHelper.lineNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import facade.Configuration;
import facade.Parser;
import sugarVisitors.Desugar;
import sugarVisitors.InjectionOnCore;
import typeSystem.TypeSystemOK;
import ast.Ast.Stage;
import ast.ErrorMessage;
import ast.ExpCore;
import ast.Ast.Path;
import ast.ExpCore.ClassB;
import auxiliaryGrammar.Norm;
import auxiliaryGrammar.Program;

public class TestTypeSystemOk {
  @RunWith(Parameterized.class)
  public static class TestOk {
    @Parameter(0) public int _lineNumber;
    @Parameter(1) public String s1;
    @Parameter(2) public String s2;
    @Parameter(3) public String s3;
    @Parameters(name = "{index}: line {0}")
    public static List<Object[]> createData() {
      return Arrays.asList(new Object[][] {
 {lineNumber(),"This0.C","{C:{method Void()}}","{C:{method Void()}##plus^##}##plus^##"
},{lineNumber(),"This0.C","{C:{method Void()} D:{}}","{C:{method Void()}##plus^## D:{}##star ^##}##plus^##"
},{lineNumber(),"This0.C",
  "{C:{class method Void foo() (This0.foo())} }",
  "{C:{class method Void foo() (This0.foo())}##star^## }##star^##"
},{lineNumber(),"This0.C",
  "{C:{class method Void foo() (D.foo())} D:{method Void() class method Void foo() (void)}}",
  "{C:{class method Void foo() (D.foo())}##plus^## D:{method Void()class method Void foo() (void)}##plus ^##}##plus^##"
},{lineNumber(),"This0.C",
  "{C:{E:{class method Void foo() (This1.foo())} class method Void foo() (D.foo())} D:{class method Void foo() (C.E.foo())}}",
  "{C:{E:{class method Void foo() (This1.foo())}##star^## class method Void foo() (D.foo())}##star^## D:{class method Void foo() (C.E.foo())}##star^##}##star^##"
},{lineNumber(),"This0.C",
  "{C:{E:{class method Void foo() (This1.foo())} class method Void foo() (D.foo())} D:{method Void() class method Void foo() (C.E.foo())}}",
  "{C:{E:{class method Void foo() (This1.foo())}##plus^## class method Void foo() (D.foo())}##plus^## D:{method Void()  class method Void foo() (C.E.foo())}##plus^##}##plus^##"

},{lineNumber(),"This0.C",
  "{K:{E:{class method Void foo() (This2.C.foo())}} C:{class method Void foo() (D.foo())} D:{class method Void foo() (K.E.foo())}}",
  "{K:{E:{class method Void foo() (This2.C.foo())}##star^##}##star ^## C:{class method Void foo() (D.foo())}##star^## D:{class method Void foo() (K.E.foo())}##star^##}##star^##"
},{lineNumber(),"This0.C",
  "{K:{method Void() E:{class method C foo() (C.foo())}} C:{class method C foo() (D.foo())} D:{class method C foo() (K.E.foo())}}",
  "{K:{method Void() E:{class method C foo() (C.foo())}##star^##}##plus ^## C:{class method C foo() (D.foo())}##star^## D:{class method C foo() (K.E.foo())}##star^##}##plus^##"
  //norm//NO, Norm is executed only in the extracted method
//},{"This0.C",
//  "{K:{E:{class method C.foo() foo() (C.foo())}} C:{class method C foo() (D.foo())} D:{class method C foo() (K.E.foo())}}",
//  "{K:{E:{class method C foo() (C.foo())}##plus^##}##plus ^## C:{class method C foo() (D.foo())}##plus^## D:{class method C foo() (K.E.foo())}##plus^##}##plus^##"
//},{"This0.C",
//  "{K:{E:{class method C.foo().foo() foo() (C.foo())}} C:{class method C foo() (D.foo())} D:{class method C foo() (K.E.foo())}}",
//  "{K:{E:{class method C foo() (C.foo())}##plus^##}##plus^## C:{class method C foo() (D.foo())}##plus^## D:{class method C foo() (K.E.foo())}##plus^##}##plus^##"
},{lineNumber(),"This0.C",
  "{C:{ method Void foo() (This0 x= this void)} }",
  "{C:{ method Void foo() (This0 x= this void)}##star^## }##star^##"
},{lineNumber(),"This0.C",
  "{C:{ method Void foo() (C x= this void)} }",
  "{C:{ method Void foo() (C x= this void)}##star^## }##star^##"


}});}

@Test()
public void testAllSteps() {//s1 unused :(
  ClassB cb1=runTypeSystem(s2);
  ClassB cb2=(ClassB)Desugar.of(Parser.parse(null,s3)).accept(new InjectionOnCore());
  TestHelper.assertEqualExp(cb1,cb2);
  }
}


@RunWith(Parameterized.class)
public static class TesFail {
  @Parameter(0) public String s1;
  @Parameter(1) public String s2;
  @Parameterized.Parameters
  public static List<Object[]> createData() {
    return Arrays.asList(new Object[][] {
    {"This0.C",
    "{C:{class method Void foo() (D.foo())} D:{class method Void bar() (void)}}"
  },{"This0.C",
   "{C:{E:{class method Void foo() (This1.foo())} class method Library foo() (D.foo())} D:{class method Void foo() (C.E.foo())}}"
  },{"This0.C",
    "{C:{E:{class method Void foo() (This1.foo())} class method Library foo() (D.foo())} D:{class method Void foo() (C.E.foo())}}"
 },{"This0.C",
  "{K:{E:{class method Any  foo() (This1.foo())}} C:{class method Void foo() (D.foo())} D:{class method Library foo() (K.E.foo())}}"

       }});}

      @Test(expected=ErrorMessage.TypeError.class)
      public void testAllSteps() {//s1 unused :(
        runTypeSystem(s2);
        assert false;
      }

      }

   static ClassB runTypeSystem(String scb1) {
        TestHelper.configureForTest();
        ClassB cb1=(ClassB)Desugar.of(Parser.parse(null,scb1)).accept(new InjectionOnCore());
        Program p=Program.empty();
        Configuration.typeSystem.computeStage(p,cb1);
        //ClassB cb1t=TypeExtraction.etFull(p,cb1);
        p=p.addAtTop(cb1);
        //assert p.checkComplete():cb1;//Not in this test?
        TypeSystemOK.checkAll(p);
        return cb1;
      }
}

