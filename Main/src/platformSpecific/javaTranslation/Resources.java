package platformSpecific.javaTranslation;

import facade.Configuration;
import facade.ErrorFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

import coreVisitors.CollectClassBs0;
import coreVisitors.CollectPaths0;
import platformSpecific.fakeInternet.PluginType;
import sugarVisitors.ToFormattedText;
import tools.Assertions;
import ast.Ast;
import ast.ErrorMessage;
import ast.ErrorMessage.UserLevelError;
import ast.ExpCore;
import ast.Ast.Doc;
import ast.Ast.Path;
import ast.Ast.SignalKind;
import ast.Ast.Stage;
import ast.ExpCore.ClassB;
import auxiliaryGrammar.EncodingHelper;
import auxiliaryGrammar.Functions;
import auxiliaryGrammar.Program;

public class Resources {
  public static final ErrorMessage.PluginActionUndefined notAct=new ErrorMessage.PluginActionUndefined(-2);
  public static final ErrorMessage.PluginActionUndefined actInEnd=new ErrorMessage.PluginActionUndefined(-1);
  private static final HashMap<String,Object> usedRes=new HashMap<>();
  public static String submitRes(Object cb){
    HashSet<String> hs=new HashSet<>(usedRes.keySet());
    String newK=Functions.freshName("key", hs);
    usedRes.put(newK,cb);
    return newK;
    }
  private static Program p;
  public static Program getP(){
    assert p!=null;
    return p;
    }
  public static <T> T withPDo(Program _p,Supplier<T> action){
    if(p!=null){throw new IllegalAccessError();}
    try{
      p=_p;
      return action.get();
      }
    finally{p=null;}
    }
  public static Object getRes(String key){
    Object o=usedRes.get(key);
    if(o==null){throw new Error("Invalid resource"+key+" Valid resources are: "+usedRes.keySet());}
    return o;
    }
  public static void clearRes() {
    usedRes.clear();
  }

  @SuppressWarnings("serial")
  public static class Error extends RuntimeException{
    public String toString() {return "Error["+ unbox +"]";}
    public final Object unbox; public Error(Object u){
      //assert !u.getClass().getName().startsWith("generated.Program42$");
      unbox=u;}
    public static Error multiPartStringError(String kind,Object ... map){
      ExpCore.ClassB cb = multiPartStringClassB(kind, map);
      return new Error(cb);
      }
    public static ExpCore.ClassB multiPartStringClassB(String kind, Object... map) throws java.lang.Error {
      List<ExpCore.ClassB.Member> ms=new ArrayList<>();
      assert map.length%2==0;
      ms.add(new ExpCore.ClassB.NestedClass(Doc.empty(),"Kind", EncodingHelper.wrapStringU(kind)));
      for(int i=0;i<map.length;i+=2){
        String cName=(String)map[i];
        ClassB inner;
        if(map[i+1] instanceof String){inner=EncodingHelper.wrapStringU((String)map[i+1]);}
        else{//for now, just doc.
          inner=new ClassB((Doc)map[i+1],Doc.empty(),false,Collections.emptyList(),Collections.emptyList(),Stage.None);
          }
        if(!Path.isValidClassName(cName)){throw Assertions.codeNotReachable("Invalid name in multiPartStringError");}
        ms.add(new ExpCore.ClassB.NestedClass(Doc.empty(), cName, inner));
      }
      ExpCore.ClassB cb=new ExpCore.ClassB(Doc.empty(), Doc.empty(), false, Collections.emptyList(), ms,Stage.None);
      return cb;
    }
    }
  @SuppressWarnings("serial")
  public static class Exception extends RuntimeException{
    public String toString() {return "Exception["+ unbox +"]";}
    public final Object unbox; public Exception(Object u){unbox=u;}
    }
  @SuppressWarnings("serial")
  public static class Return extends RuntimeException{
    public String toString() {return "Return["+ unbox +"]";}
    public final Object unbox; public Return(Object u){unbox=u;}
    }
  public static class Void{
    public static final Void instance=new Void();
    public static final Void type=new Void();
    }
  public static class Any{public static final Any type=new Any();}
  public static class Library{public static final Library type=new Library();}
  public static interface PhI<T>{
    public void commit(T t);
    public void addAction(java.util.function.Consumer<T> r);
    }
  public static interface Revertable{
    public static ast.ExpCore doRevert(Object o){
      if (o instanceof Revertable){return ((Revertable)o).revert();}
      return EncodingHelper.wrapResource(o);
    }
    public ast.ExpCore revert();
  }
  public static boolean isValid(Program p,Object res, Object[] xs) {
    ExpCore ec0=Revertable.doRevert(res);
    List<ExpCore> es=new ArrayList<>();
    for(Object o:xs){
      es.add(Revertable.doRevert(o));
    }
    boolean strict=true;
    for(ExpCore ec:es){
      List<ClassB> cbs = CollectClassBs0.of(ec);
      List<Path> ps = CollectPaths0.of(ec);
      for(ClassB cb:cbs){
        ClassB ct1= Configuration.typeSystem.typeExtraction(p,cb);
        if(ct1.getStage()==Stage.Less ||ct1.getStage()==Stage.None  ){strict=false;}
        }
      for(Path path:ps){
        if(path.isPrimitive()){continue;}
        Stage extracted=p.extractCt(path).getStage();
        if(extracted==Stage.Less || extracted==Stage.None){strict=false;}
        }
      }
    List<ClassB> cbs = CollectClassBs0.of(ec0);
    for(ClassB cb:cbs){
      ClassB ct= Configuration.typeSystem.typeExtraction(p,cb);
      try{Configuration.typeSystem.checkCt( p, ct);}
      catch(ErrorMessage msg){
        msg.printStackTrace();
        throw msg;//to breakpoint here
        }
      if(strict && (ct.getStage()==Stage.Less || ct.getStage()==Stage.None)){
        return false;
        //throw Assertions.codeNotReachable("try to make this happen, is it possible? it should mean bug in plugin code\n"/*+ToFormattedText.of(ct)*/);
      }
    }
    return true;
  }

  public static ExecutorService pluginThreads=Executors.newCachedThreadPool();
  public static <T> T block(java.util.function.Supplier<T> p){return p.get();}
  public static platformSpecific.javaTranslation.Resources.Void unused=null;

  public static interface PlgClosure<Pt extends PluginType,T>{
    T apply(Pt plg,Object[] xs);
  }
  /**
   * @param plg plugin instance
   * @param cls plugin executor
   * @param xs parameters
   * @return a safe result, or a safe error, or an non-action exception
   */
  public static <Pt extends PluginType,T> T plgExecuteSafe(Program p,Pt plg,PlgClosure<Pt,T> cls,Object ... xs){
    try{
      T res=cls.apply(plg, xs);
      if(Resources.isValid(p,res,xs)){return res;}
      else{throw Resources.notAct;}
      }
    catch(Resources.Error errF){
      if(Resources.isValid(p,errF.unbox,xs)){throw errF;}
      else{throw Resources.notAct;}
      }
    catch(ErrorMessage.PluginActionUndefined undF){throw undF;}
   //catch(java.lang.Error |RuntimeException msg){//eclipse debugger can not hande it
    catch(AssertionError msg){ throw msg;}
    catch(ErrorMessage msg){
      UserLevelError err = ErrorFormatter.formatError(p,msg);
      throw Assertions.codeNotReachable("try to make this happen, is it possible? it should mean bug in plugin code\n"+err+"\n---------------\n");
    }
    catch(NullPointerException msg){throw msg;}
    catch(RuntimeException msg){
      //throw Resources.notAct;//will be
      throw Assertions.codeNotReachable("try to make this happen, is it possible? it should mean bug in plugin code\n"+msg+"\n---------------\n");
      }
    catch(java.lang.Error msg){
      //throw Resources.notAct;//will be
      throw Assertions.codeNotReachable("try to make this happen, is it possible? it should mean bug in plugin code\n"+msg+"\n---------------\n");
      }
    catch(Throwable tF){
      //throw Resources.notAct;//will be
      throw new Error(tF);//To debug
      }
    }
  /*public Object bar(Plugin plg,Object e1, Object e2,Callable<Object> conclE){
    return plgExecutor("dbgInfo",null,new Plugin(),
        (plF,xsF)->plF.MsumInt32�xn1�xn2(xsF[0],xsF[1]),
        conclE,e1,e2);
  }*/
  public static <Pt extends PluginType,T> T plgExecutor(String plgCall,Program p,Pt plg,PlgClosure<Pt,T> cls,Callable<T> concl, Object ... es){
    //System.err.println("Executing now::"+plgCall);
    Future<T> exe=null;
    try{//for finally
      while(true){//cycle on another plugin supervision
        try{return plgExecuteSafe(p,plg,cls,es);}//call plg
        catch(ErrorMessage.PluginActionUndefined und){
          int wait=und.getWait();
          if(wait<-1){//not call me again: wait until the end and return the result
            return justGetResult(concl, exe);
            }
          //else, we are supervisionating an expression and plg will be called again
          if(exe==null){exe=pluginThreads.submit(concl);}
          justWait(exe, wait);
          }
        }
    }finally{if(exe!=null){exe.cancel(true);}}
  }
  public static <T> void justWait(Future<T> exe, int wait){
    try{
      if(wait!=-1){//timeout
        try{exe.get(wait, TimeUnit.MILLISECONDS);}
        catch(TimeoutException e){}//loop again
        }
      else {exe.get();}
      }
    catch (InterruptedException ie){
      Thread.currentThread().interrupt();
      throw new Error(ie);
      }
    catch (ExecutionException ee){
      if(ee.getCause() instanceof RuntimeException){
        return;
        //DO Nothing, just wait//throw (RuntimeException)ee.getCause();
        }
      throw new Error(ee);
      }
    }
  public static <T> T justGetResult(Callable<T> concl, Future<T> exe){
    try{
      if(exe!=null){return exe.get();}
      return concl.call();
      }
    catch (InterruptedException ie){
      Thread.currentThread().interrupt();
      throw new Error(ie);
      }
    catch (ExecutionException ee){
      if(ee.getCause() instanceof RuntimeException){
        throw (RuntimeException)ee.getCause();
        }
      throw new Error(ee);
      }
    catch (java.lang.Exception exc){
      if(exc instanceof RuntimeException){
        throw (RuntimeException)exc;
        }
      throw new Error(exc);
      }
    }
}



/*


{
PrImpl:{
Alu:{'@plugin
'L42.is/connected/withAlu
}
ExitCode:{
type method
Library normal() {'@exitStatus
'0
}
type method
Library failure() {'@exitStatus
'42000
}}
Bool:{<:Outer1::S::ToS
method
Void #checkTrue() exception Void using Outer1::Alu check ifInt32EqualDo(n1:this.binaryRepr6(), n2:Outer2::BNS::N.#numberParser(that:{'@stringU
'0
}).binaryRepr()) exception void
type method
Outer0 true() Outer0.#apply6(binaryRepr6:Outer2::BNS::N.#numberParser(that:{'@stringU
'1
}).binaryRepr())
type method
Outer0 false() Outer0.#apply6(binaryRepr6:Outer2::BNS::N.#numberParser(that:{'@stringU
'0
}).binaryRepr())
method
Outer0 #and(Outer0 that) (
  Void unused=(
    Void unused0=(
      Void unused2=this.#checkTrue()
      catch exception unused3 (
        on Void void
        )
      return that
      )
    Void unused1=return this
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #or(Outer0 that) (
  Void unused=(
    Void unused0=(
      Void unused2=this.#checkTrue()
      catch exception unused3 (
        on Void void
        )
      return this
      )
    Void unused1=return that
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #bang() (
  Void unused=(
    Void unused0=(
      Void unused2=this.#checkTrue()
      catch exception unused3 (
        on Void void
        )
      return Outer0.false()
      )
    Void unused1=return Outer0.true()
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method toS() (
  Void unused=(
    Void unused0=(
      Void unused2=this.#checkTrue()
      catch exception unused3 (
        on Void void
        )
      return Outer2::BNS::S.#stringParser(that:{'@stringU
      'true
      })
      )
    Void unused1=return Outer2::BNS::S.#stringParser(that:{'@stringU
    'false
    })
    void
    )
  catch return result (
    on Outer0::toS() result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method '@private
Outer0 #apply6(Library^binaryRepr6'@consistent
)
mut method '@consistent
Library #binaryRepr6()
read method '@consistent
Library binaryRepr6() }
URL:{<:Outer1::S::ToS
type method
Outer0 #stringParser(Library that) Outer0._private31(_binaryRepr31:that)
method
Library binaryRepr() this._binaryRepr31()
method toS() Outer2::BNS::S.#stringParser(that:this._binaryRepr31())
method
Outer1::Bool #bangequal(Outer1::URL that) this.#equalequal(that:that).#bang()
method
Outer1::Bool #equalequal(Outer1::URL that) (
  Void unused=(
    Void unused0=using Outer1::Alu check ifStringEqualDo(s1:this.binaryRepr(), s2:that.binaryRepr()) return Outer1::Bool.true()
    Void unused1=return Outer1::Bool.false()
    void
    )
  catch return result (
    on Outer1::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method '@private
Outer0 _private31(Library^_binaryRepr31'@consistent
)
mut method '@consistent
Library #_binaryRepr31()
read method '@consistent
Library _binaryRepr31() }
LinkedList:{
ListCode:{
type method
Library #apply() {' generic linked list class, elements of type Elem

Varres:{
type method
mut Outer0 #apply(Outer1^inner'@consistent
)
mut method '@consistent
Void inner(Outer1 that)
mut method '@consistent
Outer1 #inner()
read method '@consistent
Outer1 inner() }
Varres0:{
type method
mut Outer0 #apply(Outer1::#apply() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::#apply() that)
mut method '@consistent
Outer1::#apply() #inner() }
type method
Outer0 _new(Outer0::Cell^head'@consistent
)
mut method '@consistent
Outer0::Cell #head()
read method '@consistent
Outer0::Cell head()
type method
Outer0 #apply() Outer0._new(head:Outer0::CellEnd.#apply())
method
Outer3::Bool isEmpty() (
  Void unused=(
    Void unused0=(
      Outer0::head() x=this.head()
      (
        Outer0::CellEnd x0=(
          Void unused1=return x
          catch return casted (
            on Outer0::CellEnd casted

            on Any exception void
            )
          error {'@stringU
          'CastT-Should be unreachable code
          }
          )
        catch exception unused2 (
          on Void return Outer3::Bool.false()
          )
        (
          Void unused3=return Outer3::Bool.true()
          void
          )
        )
      )
    void
    )
  catch return result (
    on Outer3::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0::Elem top() this.head().#inner()
method
mut Outer0::Iterator vals() this.head().vals(terminate:Outer3::Bool.true())
method
mut Outer0::Iterator valsCut() this.head().vals(terminate:Outer3::Bool.false())
method
Outer0 pop() (
  Void unused=(
    Void unused0=return Outer0._new(head:this.head().#next())
    catch exception unused1 (
      on Void error Outer3::S.#stringParser(that:{'@stringU
      'PopOnEmpyList
      })
      )
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #plusplus(Outer0 that) (
  Void unused=(
    Outer0 res=this
    mut Outer0::Varres varres=Outer0::Varres.#apply(inner:res)
    Void unused0=(
      Outer0::vals() e=that.vals()
      (
        Void unused2=(
          Void unused3=(
            Void unused4=loop (
              Void unused5=e.#next()
              catch exception unused6 (
                on Void (
                  Void unused7=(
                    Void unused8=e.#checkEnd()
                    catch exception unused9 (
                      on Void void
                      )
                    void
                    )
                  exception void
                  )
                )
              varres.inner(that:varres.#inner().#add(that:e.#inner()))
              )
            catch exception unused10 (
              on Void void
              )
            void
            )
          catch exception propagated (
            on Any (
              Void unused11=e.#close()
              exception propagated
              )
            )
          void
          )
        catch return propagated0 (
          on Outer0 (
            Void unused12=e.#close()
            return propagated0
            )
          )
        e.#close()
        )
      )
    Void unused1=return varres.#inner()
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #add(Outer0::Elem that) Outer0._new(head:Outer0::CellNext.#apply(elem:that, nextCell:this.head()))
method
Outer0 #end() (
  Outer0::#apply() res=Outer0.#apply()
  mut Outer0::Varres0 varres=Outer0::Varres0.#apply(inner:res)
  Void unused=(
    Outer0::vals() elem=this.vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=elem.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=elem.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            varres.inner(that:varres.#inner().#add(that:elem.#inner()))
            )
          catch exception unused8 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused9=elem.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused10=elem.#close()
          return propagated0
          )
        )
      elem.#close()
      )
    )
  varres.#inner()
  )
Elem:{}
Cell:{interface
method
mut Outer1::Iterator vals(Outer4::Bool terminate)
method
Outer1::Cell #next() exception Void
method
Outer1::Elem #inner()
method
Void #checkEnd() }
CellEnd:{<:Outer1::Cell
type method
Outer0 #apply()
method vals(terminate ) Outer1::Iterator.#apply(that:this, terminate:terminate)
method #next() exception void
method #inner() error Outer4::S.#stringParser(that:{'@stringU
'InnerOnCellEnd, should not happen
})
method #checkEnd() void}
CellNext:{<:Outer1::Cell
type method
Outer0 #apply(Outer1::Elem^elem'@consistent
, Outer1::Cell^nextCell'@consistent
)
mut method '@consistent
Outer1::Elem #elem()
read method '@consistent
Outer1::Elem elem()
mut method '@consistent
Outer1::Cell #nextCell()
read method '@consistent
Outer1::Cell nextCell()
method vals(terminate ) (
  Void unused=(
    Outer1::CellNext::#apply(elem nextCell ) startPoint=Outer1::CellNext.#apply(elem:this.elem(), nextCell:this)
    Void unused0=return Outer1::Iterator.#apply(that:startPoint, terminate:terminate)
    void
    )
  catch return result (
    on Outer0::vals(terminate ) result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method #next() this.nextCell()
method #inner() this.elem()
method #checkEnd() (
  Outer0::#checkEnd() ::this::nextCell() cell=this.nextCell()
  (
    Outer1::CellNext cell0=(
      Void unused=return cell
      catch return casted (
        on Outer1::CellNext casted

        on Any exception void
        )
      error {'@stringU
      'CastT-Should be unreachable code
      }
      )
    catch exception unused0 (
      on Void void
      )
    (
      Void unused1=error Outer4::S.#stringParser(that:{'@stringU
      'IterationNotComplete,\u000a  use valsCut() to allows incomplete iterations\u000a
      })
      void
      )
    )
  )}
Iterator:{
type method
mut Outer0 #apply(Outer1::Cell^that'@consistent
, Outer4::Bool^terminate'@consistent
)
mut method '@consistent
Void that(Outer1::Cell that)
mut method '@consistent
Outer1::Cell #that()
read method '@consistent
Outer1::Cell that()
mut method '@consistent
Outer4::Bool #terminate()
read method '@consistent
Outer4::Bool terminate()
mut method
Void #next() exception Void (
  Void unused=(
    Outer0::that() ::#next() x=this.that().#next()
    Void unused0=(
      Outer1::CellEnd x0=(
        Void unused2=return x
        catch return casted (
          on Outer1::CellEnd casted

          on Any exception void
          )
        error {'@stringU
        'CastT-Should be unreachable code
        }
        )
      catch exception unused3 (
        on Void void
        )
      (
        Void unused4=exception void
        void
        )
      )
    Void unused1=return this.that(that:x)
    void
    )
  catch return result (
    on Void result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
read method
Void #checkEnd() (
  Outer0::terminate() cond=this.terminate()
  (
    Void unused=cond.#checkTrue()
    catch exception unused0 (
      on Void void
      )
    this.that().#checkEnd()
    )
  )
read method
Outer1::Elem #inner() this.that().#inner()
read method
Void #close() void}}}
type method
Library list(type Any that) using Outer0::SafeOperatorsAccess12 check redirect(that:Outer0::ListCode.#apply(), src:Outer2::BNS::S.#stringParser(that:{'@stringU
'Elem
}).binaryRepr(), dest:that) error Outer2::BNS::S.#stringParser(that:{'@stringU
'redirectNotResponsiveInsideLinkedList
})
SafeOperatorsAccess12:'@private
{'@plugin
'L42.is/connected/withSafeOperators
}}
Debug:{
type method
Void #apply(Outer2::BNS::S that) using Outer1::Alu check stringDebug(that:that.binaryRepr()) void
type method
Void #apply(Outer2::BNS::S fileName, Outer2::BNS::S content) using Outer1::Alu check fileDebug(fileName:fileName.binaryRepr(), content:content.binaryRepr()) void}
Path:{<:Outer1::S::ToS
type method
Outer0 #stringParser(Library that) Outer0._private32(_binaryRepr32:that)
method
Library binaryRepr() this._binaryRepr32()
method toS() Outer2::BNS::S.#stringParser(that:this._binaryRepr32())
method
Outer1::Bool #bangequal(Outer1::Path that) this.#equalequal(that:that).#bang()
method
Outer1::Bool #equalequal(Outer1::Path that) (
  Void unused=(
    Void unused0=using Outer1::Alu check ifStringEqualDo(s1:this.binaryRepr(), s2:that.binaryRepr()) return Outer1::Bool.true()
    Void unused1=return Outer1::Bool.false()
    void
    )
  catch return result (
    on Outer1::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer1::Path #plusplus(Outer1::Path that) (
  Void unused=(
    Void unused0=(
      Outer0::#equalequal(that ) cond=this.#equalequal(that:Outer1::Path.#stringParser(that:{'@stringU
      'Outer0
      }))
      (
        Void unused3=cond.#checkTrue()
        catch exception unused4 (
          on Void void
          )
        return that
        )
      )
    Void unused1=(
      Outer1::Path::#equalequal(that ) cond0=that.#equalequal(that:Outer1::Path.#stringParser(that:{'@stringU
      'Outer0
      }))
      (
        Void unused5=cond0.#checkTrue()
        catch exception unused6 (
          on Void void
          )
        return this
        )
      )
    Void unused2=return Outer1::Path.#stringParser(that:this.toS().#plusplus(that:Outer2::BNS::S.#stringParser(that:{'@stringU
    '::
    })).#plusplus(that:that.toS()).binaryRepr())
    void
    )
  catch return result (
    on Outer1::Path result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method '@private
Outer0 _private32(Library^_binaryRepr32'@consistent
)
mut method '@consistent
Library #_binaryRepr32()
read method '@consistent
Library _binaryRepr32() }
Selector:{<:Outer1::S::ToS
type method
Outer0 #stringParser(Library that) Outer0._private33(_binaryRepr33:that)
method
Library binaryRepr() this._binaryRepr33()
method toS() Outer2::BNS::S.#stringParser(that:this._binaryRepr33())
method
Outer1::Bool #bangequal(Outer2::BNS::S that) this.#equalequal(that:that).#bang()
method
Outer1::Bool #equalequal(Outer2::BNS::S that) (
  Void unused=(
    Void unused0=using Outer1::Alu check ifStringEqualDo(s1:this.binaryRepr(), s2:that.binaryRepr()) return Outer1::Bool.true()
    Void unused1=return Outer1::Bool.false()
    void
    )
  catch return result (
    on Outer1::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method '@private
Outer0 _private33(Library^_binaryRepr33'@consistent
)
mut method '@consistent
Library #_binaryRepr33()
read method '@consistent
Library _binaryRepr33() }
Doc:{<:Outer1::S::ToS
type method
Outer0 #stringParser(Library that) Outer0._private34(_binaryRepr34:that)
method
Library binaryRepr() this._binaryRepr34()
method toS() Outer2::BNS::S.#stringParser(that:this._binaryRepr34())
type method '@private
Outer0 _private34(Library^_binaryRepr34'@consistent
)
mut method '@consistent
Library #_binaryRepr34()
read method '@consistent
Library _binaryRepr34() }
SafeOperators:{'@plugin
'L42.is/connected/withSafeOperators

type method
Library compose(Library left, Library right) using Outer0 check compose(left:left, right:right) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'compose
}))
type method
Library renameClass(Library that, Outer1::Path src, Outer1::Path dest) using Outer0 check renameClass(that:that, src:src.binaryRepr(), dest:dest.binaryRepr()) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'renameClass
}))
type method
Library renameMethod(Library that, Outer1::Path path, Outer1::Selector src, Outer1::Selector dest) using Outer0 check renameMethod(that:that, path:path.binaryRepr(), src:src.binaryRepr(), dest:dest.binaryRepr()) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'renameMethod
}))
type method
Library redirect(Library that, Outer1::Path src, type Any dest) using Outer0 check redirect(that:that, src:src.binaryRepr(), dest:dest) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'redirect
}))
type method
Library removeImplementation(Library that, Outer1::Path path) using Outer0 check removeImplementation(that:that, path:path.binaryRepr()) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'removeImplementation(path)
}))
type method
Library removeImplementation(Library that, Outer1::Path path, Outer1::Selector selector) using Outer0 check removeImplementation(that:that, path:path.binaryRepr(), selector:selector.binaryRepr()) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'removeImplementation(path,selector)
}))
type method
Library addDocumentation(Library that, Outer1::Path path, Outer1::Doc doc) using Outer0 check addDocumentation(that:that, path:path.binaryRepr(), doc:doc.binaryRepr()) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'addDocumentation(path)
}))
type method
Library addDocumentation(Library that, Outer1::Path path, Outer1::Selector selector, Outer1::Doc doc) using Outer0 check addDocumentation(that:that, path:path.binaryRepr(), selector:selector.binaryRepr(), doc:doc.binaryRepr()) error Outer0.mkError6(that6:Outer2::BNS::S.#stringParser(that:{'@stringU
'addDocumentation(path,selector)
}))
type method '@private
Outer2::BNS::S mkError6(Outer2::BNS::S that6) Outer2::BNS::S.#stringParser(that:{'@stringU
'Plugin SafeOperators not responsive:
}).#plusplus(that:that6)}
Report:{interface <:Outer1::S::ToS}
SourceUnfit:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Introspection::Mirror^mirror'@consistent
)
mut method '@consistent
Outer1::Introspection::Mirror #mirror()
read method '@consistent
Outer1::Introspection::Mirror mirror()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'SourceUnfit
})}
ClassClash:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Introspection::Mirror^mirror'@consistent
)
mut method '@consistent
Outer1::Introspection::Mirror #mirror()
read method '@consistent
Outer1::Introspection::Mirror mirror()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'ClassClash
})}
MethodClash:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Introspection::Mirror^mirror'@consistent
)
mut method '@consistent
Outer1::Introspection::Mirror #mirror()
read method '@consistent
Outer1::Introspection::Mirror mirror()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'MethodClash
})}
InvalidOnTopLevel:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Introspection::Mirror^mirror'@consistent
)
mut method '@consistent
Outer1::Introspection::Mirror #mirror()
read method '@consistent
Outer1::Introspection::Mirror mirror()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'InvalidOnTopLevel
})}
TargetUnavailable:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Introspection::Mirror^mirror'@consistent
)
mut method '@consistent
Outer1::Introspection::Mirror #mirror()
read method '@consistent
Outer1::Introspection::Mirror mirror()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'TargetUnavailable
})}
PrivacyCoupuled:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Introspection::Mirror^mirror'@consistent
)
mut method '@consistent
Outer1::Introspection::Mirror #mirror()
read method '@consistent
Outer1::Introspection::Mirror mirror()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'PrivacyCoupuled
})}
NSBMissing:{<:Outer1::Report
type method
Outer0 #apply(Outer1::Report^report'@consistent
)
mut method '@consistent
Outer1::Report #report()
read method '@consistent
Outer1::Report report()
method toS() Outer2::BNS::S.#stringParser(that:{'@stringU
'NSBMissing
}).#plusplus(that:this.report())}
Introspection:{
VarmN:{
type method
mut Outer0 #apply(Outer3::BNS::N::#numberParser(that ) ^inner'@consistent
)
mut method '@consistent
Void inner(Outer3::BNS::N::#numberParser(that ) that)
mut method '@consistent
Outer3::BNS::N::#numberParser(that ) #inner() }
Varresult:{
type method
mut Outer0 #apply(Outer1::MemberReports::#begin() ::#end() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::MemberReports::#begin() ::#end() that)
mut method '@consistent
Outer1::MemberReports::#begin() ::#end() #inner() }
Varaccumulator:{
type method
mut Outer0 #apply(Outer1::MethodReports::#begin() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::MethodReports::#begin() that)
mut method '@consistent
Outer1::MethodReports::#begin() #inner() }
Varaccumulator0:{
type method
mut Outer0 #apply(Outer1::NestedClassReports::#begin() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::NestedClassReports::#begin() that)
mut method '@consistent
Outer1::NestedClassReports::#begin() #inner() }
type method
Outer0 #apply(Outer0::Mirror^mirror'@consistent
)
mut method '@consistent
Outer0::Mirror #mirror()
read method '@consistent
Outer0::Mirror mirror()
type method
Outer0 #apply(Library lib) Outer0.#apply(mirror:Outer0::MirrorLibrary.#apply(lib:lib))
method
Outer0::NodeReport query(Outer1::Path that) exception Outer1::TargetUnavailable this.mirror().introspectionGiveInfo(path:that)
MemberReports:{' generic linked list class, elements of type Elem

Varres:{
type method
mut Outer0 #apply(Outer1^inner'@consistent
)
mut method '@consistent
Void inner(Outer1 that)
mut method '@consistent
Outer1 #inner()
read method '@consistent
Outer1 inner() }
Varres0:{
type method
mut Outer0 #apply(Outer1::#apply() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::#apply() that)
mut method '@consistent
Outer1::#apply() #inner() }
type method
Outer0 _new(Outer0::Cell^head'@consistent
)
mut method '@consistent
Outer0::Cell #head()
read method '@consistent
Outer0::Cell head()
type method
Outer0 #apply() Outer0._new(head:Outer0::CellEnd.#apply())
method
Outer2::Bool isEmpty() (
  Void unused=(
    Void unused0=(
      Outer0::head() x=this.head()
      (
        Outer0::CellEnd x0=(
          Void unused1=return x
          catch return casted (
            on Outer0::CellEnd casted

            on Any exception void
            )
          error {'@stringU
          'CastT-Should be unreachable code
          }
          )
        catch exception unused2 (
          on Void return Outer2::Bool.false()
          )
        (
          Void unused3=return Outer2::Bool.true()
          void
          )
        )
      )
    void
    )
  catch return result (
    on Outer2::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer1::MemberReport top() this.head().#inner()
method
mut Outer0::Iterator vals() this.head().vals(terminate:Outer2::Bool.true())
method
mut Outer0::Iterator valsCut() this.head().vals(terminate:Outer2::Bool.false())
method
Outer0 pop() (
  Void unused=(
    Void unused0=return Outer0._new(head:this.head().#next())
    catch exception unused1 (
      on Void error Outer3::BNS::S.#stringParser(that:{'@stringU
      'PopOnEmpyList
      })
      )
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #plusplus(Outer0 that) (
  Void unused=(
    Outer0 res=this
    mut Outer0::Varres varres=Outer0::Varres.#apply(inner:res)
    Void unused0=(
      Outer0::vals() e=that.vals()
      (
        Void unused2=(
          Void unused3=(
            Void unused4=loop (
              Void unused5=e.#next()
              catch exception unused6 (
                on Void (
                  Void unused7=(
                    Void unused8=e.#checkEnd()
                    catch exception unused9 (
                      on Void void
                      )
                    void
                    )
                  exception void
                  )
                )
              varres.inner(that:varres.#inner().#add(that:e.#inner()))
              )
            catch exception unused10 (
              on Void void
              )
            void
            )
          catch exception propagated (
            on Any (
              Void unused11=e.#close()
              exception propagated
              )
            )
          void
          )
        catch return propagated0 (
          on Outer0 (
            Void unused12=e.#close()
            return propagated0
            )
          )
        e.#close()
        )
      )
    Void unused1=return varres.#inner()
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #add(Outer1::MemberReport that) Outer0._new(head:Outer0::CellNext.#apply(elem:that, nextCell:this.head()))
method
Outer0 #end() (
  Outer0::#apply() res=Outer0.#apply()
  mut Outer0::Varres0 varres=Outer0::Varres0.#apply(inner:res)
  Void unused=(
    Outer0::vals() elem=this.vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=elem.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=elem.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            varres.inner(that:varres.#inner().#add(that:elem.#inner()))
            )
          catch exception unused8 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused9=elem.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused10=elem.#close()
          return propagated0
          )
        )
      elem.#close()
      )
    )
  varres.#inner()
  )
Cell:{interface
method
mut Outer1::Iterator vals(Outer3::Bool terminate)
method
Outer1::Cell #next() exception Void
method
Outer2::MemberReport #inner()
method
Void #checkEnd() }
CellEnd:{<:Outer1::Cell
type method
Outer0 #apply()
method vals(terminate ) Outer1::Iterator.#apply(that:this, terminate:terminate)
method #next() exception void
method #inner() error Outer4::BNS::S.#stringParser(that:{'@stringU
'InnerOnCellEnd, should not happen
})
method #checkEnd() void}
CellNext:{<:Outer1::Cell
type method
Outer0 #apply(Outer2::MemberReport^elem'@consistent
, Outer1::Cell^nextCell'@consistent
)
mut method '@consistent
Outer2::MemberReport #elem()
read method '@consistent
Outer2::MemberReport elem()
mut method '@consistent
Outer1::Cell #nextCell()
read method '@consistent
Outer1::Cell nextCell()
method vals(terminate ) (
  Void unused=(
    Outer1::CellNext::#apply(elem nextCell ) startPoint=Outer1::CellNext.#apply(elem:this.elem(), nextCell:this)
    Void unused0=return Outer1::Iterator.#apply(that:startPoint, terminate:terminate)
    void
    )
  catch return result (
    on Outer0::vals(terminate ) result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method #next() this.nextCell()
method #inner() this.elem()
method #checkEnd() (
  Outer0::#checkEnd() ::this::nextCell() cell=this.nextCell()
  (
    Outer1::CellNext cell0=(
      Void unused=return cell
      catch return casted (
        on Outer1::CellNext casted

        on Any exception void
        )
      error {'@stringU
      'CastT-Should be unreachable code
      }
      )
    catch exception unused0 (
      on Void void
      )
    (
      Void unused1=error Outer4::BNS::S.#stringParser(that:{'@stringU
      'IterationNotComplete,\u000a  use valsCut() to allows incomplete iterations\u000a
      })
      void
      )
    )
  )}
Iterator:{
type method
mut Outer0 #apply(Outer1::Cell^that'@consistent
, Outer3::Bool^terminate'@consistent
)
mut method '@consistent
Void that(Outer1::Cell that)
mut method '@consistent
Outer1::Cell #that()
read method '@consistent
Outer1::Cell that()
mut method '@consistent
Outer3::Bool #terminate()
read method '@consistent
Outer3::Bool terminate()
mut method
Void #next() exception Void (
  Void unused=(
    Outer0::that() ::#next() x=this.that().#next()
    Void unused0=(
      Outer1::CellEnd x0=(
        Void unused2=return x
        catch return casted (
          on Outer1::CellEnd casted

          on Any exception void
          )
        error {'@stringU
        'CastT-Should be unreachable code
        }
        )
      catch exception unused3 (
        on Void void
        )
      (
        Void unused4=exception void
        void
        )
      )
    Void unused1=return this.that(that:x)
    void
    )
  catch return result (
    on Void result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
read method
Void #checkEnd() (
  Outer0::terminate() cond=this.terminate()
  (
    Void unused=cond.#checkTrue()
    catch exception unused0 (
      on Void void
      )
    this.that().#checkEnd()
    )
  )
read method
Outer2::MemberReport #inner() this.that().#inner()
read method
Void #close() void}}
MethodReports:{' generic linked list class, elements of type Elem

Varres:{
type method
mut Outer0 #apply(Outer1^inner'@consistent
)
mut method '@consistent
Void inner(Outer1 that)
mut method '@consistent
Outer1 #inner()
read method '@consistent
Outer1 inner() }
Varres0:{
type method
mut Outer0 #apply(Outer1::#apply() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::#apply() that)
mut method '@consistent
Outer1::#apply() #inner() }
type method
Outer0 _new(Outer0::Cell^head'@consistent
)
mut method '@consistent
Outer0::Cell #head()
read method '@consistent
Outer0::Cell head()
type method
Outer0 #apply() Outer0._new(head:Outer0::CellEnd.#apply())
method
Outer2::Bool isEmpty() (
  Void unused=(
    Void unused0=(
      Outer0::head() x=this.head()
      (
        Outer0::CellEnd x0=(
          Void unused1=return x
          catch return casted (
            on Outer0::CellEnd casted

            on Any exception void
            )
          error {'@stringU
          'CastT-Should be unreachable code
          }
          )
        catch exception unused2 (
          on Void return Outer2::Bool.false()
          )
        (
          Void unused3=return Outer2::Bool.true()
          void
          )
        )
      )
    void
    )
  catch return result (
    on Outer2::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer1::MethodReport top() this.head().#inner()
method
mut Outer0::Iterator vals() this.head().vals(terminate:Outer2::Bool.true())
method
mut Outer0::Iterator valsCut() this.head().vals(terminate:Outer2::Bool.false())
method
Outer0 pop() (
  Void unused=(
    Void unused0=return Outer0._new(head:this.head().#next())
    catch exception unused1 (
      on Void error Outer3::BNS::S.#stringParser(that:{'@stringU
      'PopOnEmpyList
      })
      )
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #plusplus(Outer0 that) (
  Void unused=(
    Outer0 res=this
    mut Outer0::Varres varres=Outer0::Varres.#apply(inner:res)
    Void unused0=(
      Outer0::vals() e=that.vals()
      (
        Void unused2=(
          Void unused3=(
            Void unused4=loop (
              Void unused5=e.#next()
              catch exception unused6 (
                on Void (
                  Void unused7=(
                    Void unused8=e.#checkEnd()
                    catch exception unused9 (
                      on Void void
                      )
                    void
                    )
                  exception void
                  )
                )
              varres.inner(that:varres.#inner().#add(that:e.#inner()))
              )
            catch exception unused10 (
              on Void void
              )
            void
            )
          catch exception propagated (
            on Any (
              Void unused11=e.#close()
              exception propagated
              )
            )
          void
          )
        catch return propagated0 (
          on Outer0 (
            Void unused12=e.#close()
            return propagated0
            )
          )
        e.#close()
        )
      )
    Void unused1=return varres.#inner()
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #add(Outer1::MethodReport that) Outer0._new(head:Outer0::CellNext.#apply(elem:that, nextCell:this.head()))
method
Outer0 #end() (
  Outer0::#apply() res=Outer0.#apply()
  mut Outer0::Varres0 varres=Outer0::Varres0.#apply(inner:res)
  Void unused=(
    Outer0::vals() elem=this.vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=elem.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=elem.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            varres.inner(that:varres.#inner().#add(that:elem.#inner()))
            )
          catch exception unused8 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused9=elem.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused10=elem.#close()
          return propagated0
          )
        )
      elem.#close()
      )
    )
  varres.#inner()
  )
Cell:{interface
method
mut Outer1::Iterator vals(Outer3::Bool terminate)
method
Outer1::Cell #next() exception Void
method
Outer2::MethodReport #inner()
method
Void #checkEnd() }
CellEnd:{<:Outer1::Cell
type method
Outer0 #apply()
method vals(terminate ) Outer1::Iterator.#apply(that:this, terminate:terminate)
method #next() exception void
method #inner() error Outer4::BNS::S.#stringParser(that:{'@stringU
'InnerOnCellEnd, should not happen
})
method #checkEnd() void}
CellNext:{<:Outer1::Cell
type method
Outer0 #apply(Outer2::MethodReport^elem'@consistent
, Outer1::Cell^nextCell'@consistent
)
mut method '@consistent
Outer2::MethodReport #elem()
read method '@consistent
Outer2::MethodReport elem()
mut method '@consistent
Outer1::Cell #nextCell()
read method '@consistent
Outer1::Cell nextCell()
method vals(terminate ) (
  Void unused=(
    Outer1::CellNext::#apply(elem nextCell ) startPoint=Outer1::CellNext.#apply(elem:this.elem(), nextCell:this)
    Void unused0=return Outer1::Iterator.#apply(that:startPoint, terminate:terminate)
    void
    )
  catch return result (
    on Outer0::vals(terminate ) result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method #next() this.nextCell()
method #inner() this.elem()
method #checkEnd() (
  Outer0::#checkEnd() ::this::nextCell() cell=this.nextCell()
  (
    Outer1::CellNext cell0=(
      Void unused=return cell
      catch return casted (
        on Outer1::CellNext casted

        on Any exception void
        )
      error {'@stringU
      'CastT-Should be unreachable code
      }
      )
    catch exception unused0 (
      on Void void
      )
    (
      Void unused1=error Outer4::BNS::S.#stringParser(that:{'@stringU
      'IterationNotComplete,\u000a  use valsCut() to allows incomplete iterations\u000a
      })
      void
      )
    )
  )}
Iterator:{
type method
mut Outer0 #apply(Outer1::Cell^that'@consistent
, Outer3::Bool^terminate'@consistent
)
mut method '@consistent
Void that(Outer1::Cell that)
mut method '@consistent
Outer1::Cell #that()
read method '@consistent
Outer1::Cell that()
mut method '@consistent
Outer3::Bool #terminate()
read method '@consistent
Outer3::Bool terminate()
mut method
Void #next() exception Void (
  Void unused=(
    Outer0::that() ::#next() x=this.that().#next()
    Void unused0=(
      Outer1::CellEnd x0=(
        Void unused2=return x
        catch return casted (
          on Outer1::CellEnd casted

          on Any exception void
          )
        error {'@stringU
        'CastT-Should be unreachable code
        }
        )
      catch exception unused3 (
        on Void void
        )
      (
        Void unused4=exception void
        void
        )
      )
    Void unused1=return this.that(that:x)
    void
    )
  catch return result (
    on Void result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
read method
Void #checkEnd() (
  Outer0::terminate() cond=this.terminate()
  (
    Void unused=cond.#checkTrue()
    catch exception unused0 (
      on Void void
      )
    this.that().#checkEnd()
    )
  )
read method
Outer2::MethodReport #inner() this.that().#inner()
read method
Void #close() void}}
NestedClassReports:{' generic linked list class, elements of type Elem

Varres:{
type method
mut Outer0 #apply(Outer1^inner'@consistent
)
mut method '@consistent
Void inner(Outer1 that)
mut method '@consistent
Outer1 #inner()
read method '@consistent
Outer1 inner() }
Varres0:{
type method
mut Outer0 #apply(Outer1::#apply() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::#apply() that)
mut method '@consistent
Outer1::#apply() #inner() }
type method
Outer0 _new(Outer0::Cell^head'@consistent
)
mut method '@consistent
Outer0::Cell #head()
read method '@consistent
Outer0::Cell head()
type method
Outer0 #apply() Outer0._new(head:Outer0::CellEnd.#apply())
method
Outer2::Bool isEmpty() (
  Void unused=(
    Void unused0=(
      Outer0::head() x=this.head()
      (
        Outer0::CellEnd x0=(
          Void unused1=return x
          catch return casted (
            on Outer0::CellEnd casted

            on Any exception void
            )
          error {'@stringU
          'CastT-Should be unreachable code
          }
          )
        catch exception unused2 (
          on Void return Outer2::Bool.false()
          )
        (
          Void unused3=return Outer2::Bool.true()
          void
          )
        )
      )
    void
    )
  catch return result (
    on Outer2::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer1::NestedClassReport top() this.head().#inner()
method
mut Outer0::Iterator vals() this.head().vals(terminate:Outer2::Bool.true())
method
mut Outer0::Iterator valsCut() this.head().vals(terminate:Outer2::Bool.false())
method
Outer0 pop() (
  Void unused=(
    Void unused0=return Outer0._new(head:this.head().#next())
    catch exception unused1 (
      on Void error Outer3::BNS::S.#stringParser(that:{'@stringU
      'PopOnEmpyList
      })
      )
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #plusplus(Outer0 that) (
  Void unused=(
    Outer0 res=this
    mut Outer0::Varres varres=Outer0::Varres.#apply(inner:res)
    Void unused0=(
      Outer0::vals() e=that.vals()
      (
        Void unused2=(
          Void unused3=(
            Void unused4=loop (
              Void unused5=e.#next()
              catch exception unused6 (
                on Void (
                  Void unused7=(
                    Void unused8=e.#checkEnd()
                    catch exception unused9 (
                      on Void void
                      )
                    void
                    )
                  exception void
                  )
                )
              varres.inner(that:varres.#inner().#add(that:e.#inner()))
              )
            catch exception unused10 (
              on Void void
              )
            void
            )
          catch exception propagated (
            on Any (
              Void unused11=e.#close()
              exception propagated
              )
            )
          void
          )
        catch return propagated0 (
          on Outer0 (
            Void unused12=e.#close()
            return propagated0
            )
          )
        e.#close()
        )
      )
    Void unused1=return varres.#inner()
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #add(Outer1::NestedClassReport that) Outer0._new(head:Outer0::CellNext.#apply(elem:that, nextCell:this.head()))
method
Outer0 #end() (
  Outer0::#apply() res=Outer0.#apply()
  mut Outer0::Varres0 varres=Outer0::Varres0.#apply(inner:res)
  Void unused=(
    Outer0::vals() elem=this.vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=elem.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=elem.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            varres.inner(that:varres.#inner().#add(that:elem.#inner()))
            )
          catch exception unused8 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused9=elem.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused10=elem.#close()
          return propagated0
          )
        )
      elem.#close()
      )
    )
  varres.#inner()
  )
Cell:{interface
method
mut Outer1::Iterator vals(Outer3::Bool terminate)
method
Outer1::Cell #next() exception Void
method
Outer2::NestedClassReport #inner()
method
Void #checkEnd() }
CellEnd:{<:Outer1::Cell
type method
Outer0 #apply()
method vals(terminate ) Outer1::Iterator.#apply(that:this, terminate:terminate)
method #next() exception void
method #inner() error Outer4::BNS::S.#stringParser(that:{'@stringU
'InnerOnCellEnd, should not happen
})
method #checkEnd() void}
CellNext:{<:Outer1::Cell
type method
Outer0 #apply(Outer2::NestedClassReport^elem'@consistent
, Outer1::Cell^nextCell'@consistent
)
mut method '@consistent
Outer2::NestedClassReport #elem()
read method '@consistent
Outer2::NestedClassReport elem()
mut method '@consistent
Outer1::Cell #nextCell()
read method '@consistent
Outer1::Cell nextCell()
method vals(terminate ) (
  Void unused=(
    Outer1::CellNext::#apply(elem nextCell ) startPoint=Outer1::CellNext.#apply(elem:this.elem(), nextCell:this)
    Void unused0=return Outer1::Iterator.#apply(that:startPoint, terminate:terminate)
    void
    )
  catch return result (
    on Outer0::vals(terminate ) result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method #next() this.nextCell()
method #inner() this.elem()
method #checkEnd() (
  Outer0::#checkEnd() ::this::nextCell() cell=this.nextCell()
  (
    Outer1::CellNext cell0=(
      Void unused=return cell
      catch return casted (
        on Outer1::CellNext casted

        on Any exception void
        )
      error {'@stringU
      'CastT-Should be unreachable code
      }
      )
    catch exception unused0 (
      on Void void
      )
    (
      Void unused1=error Outer4::BNS::S.#stringParser(that:{'@stringU
      'IterationNotComplete,\u000a  use valsCut() to allows incomplete iterations\u000a
      })
      void
      )
    )
  )}
Iterator:{
type method
mut Outer0 #apply(Outer1::Cell^that'@consistent
, Outer3::Bool^terminate'@consistent
)
mut method '@consistent
Void that(Outer1::Cell that)
mut method '@consistent
Outer1::Cell #that()
read method '@consistent
Outer1::Cell that()
mut method '@consistent
Outer3::Bool #terminate()
read method '@consistent
Outer3::Bool terminate()
mut method
Void #next() exception Void (
  Void unused=(
    Outer0::that() ::#next() x=this.that().#next()
    Void unused0=(
      Outer1::CellEnd x0=(
        Void unused2=return x
        catch return casted (
          on Outer1::CellEnd casted

          on Any exception void
          )
        error {'@stringU
        'CastT-Should be unreachable code
        }
        )
      catch exception unused3 (
        on Void void
        )
      (
        Void unused4=exception void
        void
        )
      )
    Void unused1=return this.that(that:x)
    void
    )
  catch return result (
    on Void result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
read method
Void #checkEnd() (
  Outer0::terminate() cond=this.terminate()
  (
    Void unused=cond.#checkTrue()
    catch exception unused0 (
      on Void void
      )
    this.that().#checkEnd()
    )
  )
read method
Outer2::NestedClassReport #inner() this.that().#inner()
read method
Void #close() void}}
method
Outer0::MemberReports members(Outer1::Path that) exception Outer1::TargetUnavailable (
  Outer0::MemberReports::#begin() ::#end() result=Outer0::MemberReports.#begin().#end()
  Outer2::BNS::N::#numberParser(that ) mN=Outer2::BNS::N.#numberParser(that:{'@stringU
  '1
  })
  mut Outer0::Varresult varresult=Outer0::Varresult.#apply(inner:result)
  mut Outer0::VarmN varmN=Outer0::VarmN.#apply(inner:mN)
  Void unused=(
    Void unused0=loop (
      Void unused1=Outer1::Bool.true().#checkTrue()
      (' to be able to break

        Void unused2=varresult.inner(that:varresult.#inner().#add(that:this.mirror().introspectionGiveInfoMember(path:that, memberN:varmN.#inner())))
        varmN.inner(that:varmN.#inner().#plus(that:Outer2::BNS::N.#numberParser(that:{'@stringU
        '1
        })))
        )
      )
    catch exception unused3 (
      on Void void
      )
    void
    )
  varresult.#inner()
  )
method
Outer0::MethodReports methods(Outer1::Path that) exception Outer1::TargetUnavailable (
  Outer0::MethodReports::#begin() accumulator=Outer0::MethodReports.#begin()
  mut Outer0::Varaccumulator varaccumulator=Outer0::Varaccumulator.#apply(inner:accumulator)
  Void unused=(
    Outer0::members(that ) ::vals() m=this.members(that:that).vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=m.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=m.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            (
              Outer0::MethodReport m0=(
                Void unused8=return m.#inner()
                catch return casted (
                  on Outer0::MethodReport casted

                  on Any exception void
                  )
                error {'@stringU
                'CastT-Should be unreachable code
                }
                )
              catch exception unused9 (
                on Void void
                )
              (
                Void unused10=varaccumulator.inner(that:varaccumulator.#inner().#add(that:m0))
                void
                )
              )
            )
          catch exception unused11 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused12=m.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused13=m.#close()
          return propagated0
          )
        )
      m.#close()
      )
    )
  varaccumulator.#inner().#end()
  )
method
Outer0::NestedClassReports nestedClasses(Outer1::Path that) exception Outer1::TargetUnavailable (
  Outer0::NestedClassReports::#begin() accumulator=Outer0::NestedClassReports.#begin()
  mut Outer0::Varaccumulator0 varaccumulator=Outer0::Varaccumulator0.#apply(inner:accumulator)
  Void unused=(
    Outer0::members(that ) ::vals() m=this.members(that:that).vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=m.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=m.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            (
              Outer0::NestedClassReport m0=(
                Void unused8=return m.#inner()
                catch return casted (
                  on Outer0::NestedClassReport casted

                  on Any exception void
                  )
                error {'@stringU
                'CastT-Should be unreachable code
                }
                )
              catch exception unused9 (
                on Void void
                )
              (
                Void unused10=varaccumulator.inner(that:varaccumulator.#inner().#add(that:m0))
                void
                )
              )
            )
          catch exception unused11 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused12=m.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused13=m.#close()
          return propagated0
          )
        )
      m.#close()
      )
    )
  varaccumulator.#inner().#end()
  )
method
Outer0::MethodReports methods() (
  Void unused=(
    Void unused0=return this.methods(that:Outer1::Path.#stringParser(that:{'@stringU
    'Outer0
    }))
    catch exception x (
      on Outer1::TargetUnavailable error x
      )
    void
    )
  catch return result (
    on Outer0::MethodReports result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0::NestedClassReports nestedClasses() (
  Void unused=(
    Void unused0=return this.nestedClasses(that:Outer1::Path.#stringParser(that:{'@stringU
    'Outer0
    }))
    catch exception x (
      on Outer1::TargetUnavailable error x
      )
    void
    )
  catch return result (
    on Outer0::NestedClassReports result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0::MemberReports members() (
  Void unused=(
    Void unused0=return this.members(that:Outer1::Path.#stringParser(that:{'@stringU
    'Outer0
    }))
    catch exception x (
      on Outer1::TargetUnavailable error x
      )
    void
    )
  catch return result (
    on Outer0::MemberReports result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
Mirror:{interface
method
Outer1::NodeReport introspectionGiveInfo(Outer2::Path path) exception Outer2::TargetUnavailable
method
Outer1::MemberReport introspectionGiveInfoMember(Outer2::Path path, Outer3::BNS::N memberN) exception Outer2::TargetUnavailable, Void
method
Outer1::TypeReport introspectionGiveInfoType(Outer2::Path path, Outer3::BNS::N memberN, Outer3::BNS::N typeN) exception Outer2::TargetUnavailable, Void
method
Outer3::BNS::S introspectionDocAsString(Outer2::Path path) exception Outer2::TargetUnavailable
method
Outer3::BNS::S introspectionDocAsString(Outer2::Path path, Outer3::BNS::N annotationN) exception Outer2::TargetUnavailable, Void
method
type Any introspectionDocPath(Outer2::Path path, Outer3::BNS::N annotationN) exception Outer2::TargetUnavailable, Void }
MirrorLibrary:{<:Outer1::Mirror
type method
Outer0 #apply(Library^lib'@consistent
)
mut method '@consistent
Library #lib()
read method '@consistent
Library lib()
method introspectionGiveInfo(path ) Outer1::NodeReport.#apply(report:Outer1::MirrorLibrary.#apply(lib:using Outer2::SafeOperators check introspectionGiveInfo(that:this.lib(), path:path.binaryRepr()) error Outer2::SafeOperators.mkError6(that6:Outer3::BNS::S.#stringParser(that:{'@stringU
'introspectionGiveInfo
}))))
method introspectionGiveInfoMember(path memberN ) Outer1.dispatchReport(that:using Outer2::SafeOperators check introspectionGiveInfoMember(that:this.lib(), path:path.binaryRepr(), memberN:memberN.binaryRepr()) exception void)
method introspectionGiveInfoType(path memberN typeN ) Outer1::TypeReport.#apply(report:Outer1::MirrorLibrary.#apply(lib:using Outer2::SafeOperators check introspectionGiveInfoType(that:this.lib(), path:path.binaryRepr(), memberN:memberN.binaryRepr(), typeN:typeN.binaryRepr()) exception void))
method introspectionDocAsString(path annotationN ) Outer3::BNS::S.#stringParser(that:using Outer2::SafeOperators check introspectionDocAsString(that:this.lib(), path:path.binaryRepr(), annotationN:Outer3::BNS::N.#numberParser(that:{'@stringU
'1
}).#plus(that:annotationN).binaryRepr()) exception void)
method introspectionDocAsString(path ) Outer3::BNS::S.#stringParser(that:using Outer2::SafeOperators check introspectionDocAsString(that:this.lib(), path:path.binaryRepr(), annotationN:Outer3::BNS::N.#numberParser(that:{'@stringU
'0
}).binaryRepr()) error Outer2::SafeOperators.mkError6(that6:Outer3::BNS::S.#stringParser(that:{'@stringU
'introspectionDocAsString
})))
method introspectionDocPath(path annotationN ) using Outer2::SafeOperators check introspectionDocPath(that:this.lib(), path:path.binaryRepr(), annotationN:annotationN.binaryRepr()) error Outer2::SafeOperators.mkError6(that6:Outer3::BNS::S.#stringParser(that:{'@stringU
'introspectionDocPath
}))}
NodeReport:{
type method
Outer0 #apply(Outer1::Mirror^report'@consistent
)
mut method '@consistent
Outer1::Mirror #report()
read method '@consistent
Outer1::Mirror report()
method
Outer3::BNS::S classKind() Outer1.#apply(that:this.report(), p:Outer2::Path.#stringParser(that:{'@stringU
'ClassKind
}))}
TypeReport:{
type method
Outer0 #apply(Outer1::Mirror^report'@consistent
)
mut method '@consistent
Outer1::Mirror #report()
read method '@consistent
Outer1::Mirror report() }
MemberReport:{interface }
MethodReport:{<:Outer1::MemberReport
type method
Outer0 #apply(Outer1::Mirror^report'@consistent
)
mut method '@consistent
Outer1::Mirror #report()
read method '@consistent
Outer1::Mirror report()
method
Outer2::Selector key() Outer2::Selector.#stringParser(that:Outer1.#apply(that:this.report(), p:Outer2::Path.#stringParser(that:{'@stringU
'Key
})).binaryRepr())}
NestedClassReport:{<:Outer1::MemberReport
type method
Outer0 #apply(Outer1::Mirror^report'@consistent
)
mut method '@consistent
Outer1::Mirror #report()
read method '@consistent
Outer1::Mirror report() }
type method
Outer0::MemberReport dispatchReport(Library that) (
  Void unused=(
    Outer0::MirrorLibrary::#apply(lib ) mirror=Outer0::MirrorLibrary.#apply(lib:that)
    Void unused0=(
      Outer0::#apply(that p ) x=Outer0.#apply(that:mirror, p:Outer1::Path.#stringParser(that:{'@stringU
      'MemberKind
      }))
      (
        Outer0::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer2::BNS::S.#stringParser(that:{'@stringU
        'NestedClass
        }))
        (
          Void unused1=cond.#checkTrue()
          catch exception unused2 (
            on Void (
              Outer0::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer2::BNS::S.#stringParser(that:{'@stringU
              'InterfaceImplementedMethod
              }))
              (
                Void unused3=cond0.#checkTrue()
                catch exception unused4 (
                  on Void (
                    Outer0::#apply(that p ) ::#equalequal(that ) cond1=x.#equalequal(that:Outer2::BNS::S.#stringParser(that:{'@stringU
                    'ImplementedMethod
                    }))
                    (
                      Void unused5=cond1.#checkTrue()
                      catch exception unused6 (
                        on Void (
                          Outer0::#apply(that p ) ::#equalequal(that ) cond2=x.#equalequal(that:Outer2::BNS::S.#stringParser(that:{'@stringU
                          'AbstractMethod
                          }))
                          (
                            Void unused7=cond2.#checkTrue()
                            catch exception unused8 (
                              on Void error Outer2::BNS::S.#stringParser(that:{'@stringU
                              'invalid member kind
                              }).#plusplus(that:x)
                              )
                            return Outer0::MethodReport.#apply(report:mirror)
                            )
                          )
                        )
                      return Outer0::MethodReport.#apply(report:mirror)
                      )
                    )
                  )
                return Outer0::MethodReport.#apply(report:mirror)
                )
              )
            )
          return Outer0::NestedClassReport.#apply(report:mirror)
          )
        )
      )
    void
    )
  catch return result (
    on Outer0::MemberReport result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer2::BNS::S #apply(Outer0::Mirror that, Outer1::Path p) (
  Outer0::Mirror::introspectionDocAsString(path ) result=that.introspectionDocAsString(path:p)
  catch exception x (
    on Outer1::TargetUnavailable error x
    )
  result
  )}
Opt:{
type method
Library #apply(type Any that) Outer1::SafeOperators.redirect(that:{
type method
Outer0 #new(Outer0::TOpt^that'@consistent
)
mut method '@consistent
Outer0::TOpt #that()
read method '@consistent
Outer0::TOpt that()
T:{}
TOpt:{interface
method
Outer1::T get() }
TEmpty:{<:Outer1::TOpt
type method
Outer0 #apply()
method get() error Outer3::S.#stringParser(that:{'@stringU
'Value not present
})}
TOf:{<:Outer1::TOpt
type method
Outer0 #apply(Outer1::T^that'@consistent
)
mut method '@consistent
Outer1::T #that()
read method '@consistent
Outer1::T that()
method get() this.that()}
method
Outer0::T #tilde() this.that().get()
method
Outer2::Bool isPresent() (
  Void unused=(
    Outer0::#tilde() aux=this.#tilde()
    Void unused0=return Outer2::Bool.true()
    catch error unused1 (
      on Any return Outer2::Bool.false()
      )
    void
    )
  catch return result (
    on Outer2::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #apply() Outer0.#new(that:Outer0::TEmpty.#apply())
type method
Outer0 #apply(Outer0::T that) Outer0.#new(that:Outer0::TOf.#apply(that:that))}, src:Outer1::Path.#stringParser(that:{'@stringU
'T
}), dest:that)}
Compose:{
Varresult0:{
type method
mut Outer0 #apply(Outer1::myLibs() ::vals() ::#inner() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer1::myLibs() ::vals() ::#inner() that)
mut method '@consistent
Outer1::myLibs() ::vals() ::#inner() #inner() }
type method
Outer0 #apply(Outer0::Resolver^resolver'@consistent
, Outer0::Libs6^myLibs'@consistent
)
mut method '@consistent
Outer0::Resolver #resolver()
read method '@consistent
Outer0::Resolver resolver()
mut method '@consistent
Outer0::Libs6 #myLibs()
read method '@consistent
Outer0::Libs6 myLibs()
Resolver:{
type method
Outer0 #new(Outer0::TOpt^that'@consistent
)
mut method '@consistent
Outer0::TOpt #that()
read method '@consistent
Outer0::TOpt that()
TOpt:{interface
method
Library get() }
TEmpty:{<:Outer1::TOpt
type method
Outer0 #apply()
method get() error Outer4::BNS::S.#stringParser(that:{'@stringU
'Value not present
})}
TOf:{<:Outer1::TOpt
type method
Outer0 #apply(Library^that'@consistent
)
mut method '@consistent
Library #that()
read method '@consistent
Library that()
method get() this.that()}
method
Library #tilde() this.that().get()
method
Outer2::Bool isPresent() (
  Void unused=(
    Outer0::#tilde() aux=this.#tilde()
    Void unused0=return Outer2::Bool.true()
    catch error unused1 (
      on Any return Outer2::Bool.false()
      )
    void
    )
  catch return result (
    on Outer2::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #apply() Outer0.#new(that:Outer0::TEmpty.#apply())
type method
Outer0 #apply(Library that) Outer0.#new(that:Outer0::TOf.#apply(that:that))}
type method
Outer0 #apply() Outer0.#apply(resolver:Outer0::Resolver.#apply(), myLibs:Outer0::Libs6.#begin().#end())
method
Outer0 #add(Library that) (
  Outer0::myLibs() ::#add(that ) ls=this.myLibs().#add(that:that)
  Outer0.#apply(resolver:this.resolver(), myLibs:ls)
  )
method
Outer0 #add(Library resolver) Outer0.#apply(resolver:Outer0::Resolver.#apply(that:resolver), myLibs:this.myLibs())
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #end() this
method
Library #leftleft(Library that) exception Outer1::ClassClash, Outer1::MethodClash (
  Void unused=(
    Outer0::myLibs() ::vals() it=this.myLibs().vals()
    Void unused0=it.#next()
    catch exception unused1 (
      on Void return that
      )
    (
      Outer0::myLibs() ::vals() ::#inner() result=it.#inner()
      mut Outer0::Varresult0 varresult=Outer0::Varresult0.#apply(inner:result)
      Void unused2=(
        Outer0::myLibs() ::vals() libi=it
        (
          Void unused4=(
            Void unused5=(
              Void unused6=loop (
                Void unused7=libi.#next()
                catch exception unused8 (
                  on Void (
                    Void unused9=(
                      Void unused10=libi.#checkEnd()
                      catch exception unused11 (
                        on Void void
                        )
                      void
                      )
                    exception void
                    )
                  )
                varresult.inner(that:this.accumulate(l1:varresult.#inner(), l2:libi.#inner()))
                )
              catch exception unused12 (
                on Void void
                )
              void
              )
            catch exception propagated (
              on Any (
                Void unused13=libi.#close()
                exception propagated
                )
              )
            void
            )
          catch return propagated0 (
            on Library (
              Void unused14=libi.#close()
              return propagated0
              )
            )
          libi.#close()
          )
        )
      Void unused3=return this.accumulate(l1:varresult.#inner(), l2:that)
      void
      )
    )
  catch return result0 (
    on Library result0
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Library accumulate(Library l1, Library l2) exception Outer1::ClassClash, Outer1::MethodClash (
  Void unused=(
    Void unused0=return this.liftErrorsCompose6(left6:l1, right6:l2)
    catch exception x (
      on Any (
        Void unused1=(
          Outer0::resolver() ::isPresent() ::#bang() cond=this.resolver().isPresent().#bang()
          (
            Void unused5=cond.#checkTrue()
            catch exception unused6 (
              on Void void
              )
            error x
            )
          )
        Outer1::Introspection::#apply(lib ) ::methods() ms=Outer1::Introspection.#apply(lib:this.resolver().#tilde()).methods()
        Outer1::Selector name=ms.pop().pop().top().key()
        Outer1::Selector nameLeft=ms.pop().top().key()
        Outer1::Selector nameRight=ms.top().key()
        Void unused2=Outer1::Debug.#apply(that:Outer2::BNS::S.#stringParser(that:{'@stringU
        'name is:
        }).#plusplus(that:name))
        Void unused3=Outer1::Debug.#apply(that:Outer2::BNS::S.#stringParser(that:{'@stringU
        'nameLeft is:
        }).#plusplus(that:nameLeft))
        Void unused4=Outer1::Debug.#apply(that:Outer2::BNS::S.#stringParser(that:{'@stringU
        'nameRight is:
        }).#plusplus(that:nameRight))
        error Outer2::BNS::S.#stringParser(that:{'@stringU
        'DO ADAPTER
        })
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #plusplus(Outer0 that) (
  Void unused=(
    Outer0::myLibs() ::#plusplus(that ) list=this.myLibs().#plusplus(that:that.myLibs())
    Outer0::Resolver res=(
      Void unused1=(
        Void unused2=(
          Outer0::resolver() ::isPresent() cond=that.resolver().isPresent()
          (
            Void unused4=cond.#checkTrue()
            catch exception unused5 (
              on Void void
              )
            return that.resolver()
            )
          )
        Void unused3=return this.resolver()
        void
        )
      catch return result0 (
        on Outer0::Resolver result0
        )
      error {'@stringU
      'CurlyBlock-Should be unreachable code
      }
      )
    Void unused0=return Outer0.#apply(resolver:res, myLibs:list)
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method '@private
Library liftErrorsCompose6(Library left6, Library right6) exception Outer1::ClassClash, Outer1::MethodClash (
  Void unused=(
    Void unused0=return Outer1::SafeOperators.compose(left:left6, right:right6)
    catch error err (
      on Library (
        Outer1::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer1::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer1::Introspection::#apply(that p ) x=Outer1::Introspection.#apply(that:mirror, p:Outer1::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer1::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer2::BNS::S.#stringParser(that:{'@stringU
            'ClassClash
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void (
                  Outer1::Introspection::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer2::BNS::S.#stringParser(that:{'@stringU
                  'MethodClash
                  }))
                  (
                    Void unused3=cond0.#checkTrue()
                    catch exception unused4 (
                      on Void error err
                      )
                    exception Outer1::MethodClash.#apply(mirror:mirror)
                    )
                  )
                )
              exception Outer1::ClassClash.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
Libs6:'@private
{' generic linked list class, elements of type Elem

Varres:{
type method
mut Outer0 #apply(Outer2::Libs6^inner'@consistent
)
mut method '@consistent
Void inner(Outer2::Libs6 that)
mut method '@consistent
Outer2::Libs6 #inner()
read method '@consistent
Outer2::Libs6 inner() }
Varres0:{
type method
mut Outer0 #apply(Outer2::Libs6::#apply() ^inner'@consistent
)
mut method '@consistent
Void inner(Outer2::Libs6::#apply() that)
mut method '@consistent
Outer2::Libs6::#apply() #inner() }
type method
Outer0 _new(Outer1::Libs6::Cell^head'@consistent
)
mut method '@consistent
Outer1::Libs6::Cell #head()
read method '@consistent
Outer1::Libs6::Cell head()
type method
Outer0 #apply() Outer0._new(head:Outer1::Libs6::CellEnd.#apply())
method
Outer2::Bool isEmpty() (
  Void unused=(
    Void unused0=(
      Outer0::head() x=this.head()
      (
        Outer1::Libs6::CellEnd x0=(
          Void unused1=return x
          catch return casted (
            on Outer1::Libs6::CellEnd casted

            on Any exception void
            )
          error {'@stringU
          'CastT-Should be unreachable code
          }
          )
        catch exception unused2 (
          on Void return Outer2::Bool.false()
          )
        (
          Void unused3=return Outer2::Bool.true()
          void
          )
        )
      )
    void
    )
  catch return result (
    on Outer2::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Library top() this.head().#inner()
method
mut Outer1::Libs6::Iterator vals() this.head().vals(terminate:Outer2::Bool.true())
method
mut Outer1::Libs6::Iterator valsCut() this.head().vals(terminate:Outer2::Bool.false())
method
Outer0 pop() (
  Void unused=(
    Void unused0=return Outer0._new(head:this.head().#next())
    catch exception unused1 (
      on Void error Outer3::BNS::S.#stringParser(that:{'@stringU
      'PopOnEmpyList
      })
      )
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #plusplus(Outer0 that) (
  Void unused=(
    Outer0 res=this
    mut Outer1::Libs6::Varres varres=Outer1::Libs6::Varres.#apply(inner:res)
    Void unused0=(
      Outer0::vals() e=that.vals()
      (
        Void unused2=(
          Void unused3=(
            Void unused4=loop (
              Void unused5=e.#next()
              catch exception unused6 (
                on Void (
                  Void unused7=(
                    Void unused8=e.#checkEnd()
                    catch exception unused9 (
                      on Void void
                      )
                    void
                    )
                  exception void
                  )
                )
              varres.inner(that:varres.#inner().#add(that:e.#inner()))
              )
            catch exception unused10 (
              on Void void
              )
            void
            )
          catch exception propagated (
            on Any (
              Void unused11=e.#close()
              exception propagated
              )
            )
          void
          )
        catch return propagated0 (
          on Outer0 (
            Void unused12=e.#close()
            return propagated0
            )
          )
        e.#close()
        )
      )
    Void unused1=return varres.#inner()
    void
    )
  catch return result (
    on Outer0 result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #add(Library that) Outer0._new(head:Outer1::Libs6::CellNext.#apply(elem:that, nextCell:this.head()))
method
Outer0 #end() (
  Outer0::#apply() res=Outer0.#apply()
  mut Outer1::Libs6::Varres0 varres=Outer1::Libs6::Varres0.#apply(inner:res)
  Void unused=(
    Outer0::vals() elem=this.vals()
    (
      Void unused0=(
        Void unused1=(
          Void unused2=loop (
            Void unused3=elem.#next()
            catch exception unused4 (
              on Void (
                Void unused5=(
                  Void unused6=elem.#checkEnd()
                  catch exception unused7 (
                    on Void void
                    )
                  void
                  )
                exception void
                )
              )
            varres.inner(that:varres.#inner().#add(that:elem.#inner()))
            )
          catch exception unused8 (
            on Void void
            )
          void
          )
        catch exception propagated (
          on Any (
            Void unused9=elem.#close()
            exception propagated
            )
          )
        void
        )
      catch return propagated0 (
        on Any (
          Void unused10=elem.#close()
          return propagated0
          )
        )
      elem.#close()
      )
    )
  varres.#inner()
  )
Cell:{interface
method
mut Outer2::Libs6::Iterator vals(Outer3::Bool terminate)
method
Outer1::Cell #next() exception Void
method
Library #inner()
method
Void #checkEnd() }
CellEnd:{<:Outer2::Libs6::Cell
type method
Outer0 #apply()
method vals(terminate ) Outer2::Libs6::Iterator.#apply(that:this, terminate:terminate)
method #next() exception void
method #inner() error Outer4::BNS::S.#stringParser(that:{'@stringU
'InnerOnCellEnd, should not happen
})
method #checkEnd() void}
CellNext:{<:Outer2::Libs6::Cell
type method
Outer0 #apply(Library^elem'@consistent
, Outer2::Libs6::Cell^nextCell'@consistent
)
mut method '@consistent
Library #elem()
read method '@consistent
Library elem()
mut method '@consistent
Outer2::Libs6::Cell #nextCell()
read method '@consistent
Outer2::Libs6::Cell nextCell()
method vals(terminate ) (
  Void unused=(
    Outer1::CellNext::#apply(elem nextCell ) startPoint=Outer1::CellNext.#apply(elem:this.elem(), nextCell:this)
    Void unused0=return Outer2::Libs6::Iterator.#apply(that:startPoint, terminate:terminate)
    void
    )
  catch return result (
    on Outer0::vals(terminate ) result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method #next() this.nextCell()
method #inner() this.elem()
method #checkEnd() (
  Outer0::#checkEnd() ::this::nextCell() cell=this.nextCell()
  (
    Outer1::CellNext cell0=(
      Void unused=return cell
      catch return casted (
        on Outer1::CellNext casted

        on Any exception void
        )
      error {'@stringU
      'CastT-Should be unreachable code
      }
      )
    catch exception unused0 (
      on Void void
      )
    (
      Void unused1=error Outer4::BNS::S.#stringParser(that:{'@stringU
      'IterationNotComplete,\u000a  use valsCut() to allows incomplete iterations\u000a
      })
      void
      )
    )
  )}
Iterator:{
type method
mut Outer0 #apply(Outer2::Libs6::Cell^that'@consistent
, Outer3::Bool^terminate'@consistent
)
mut method '@consistent
Void that(Outer2::Libs6::Cell that)
mut method '@consistent
Outer2::Libs6::Cell #that()
read method '@consistent
Outer2::Libs6::Cell that()
mut method '@consistent
Outer3::Bool #terminate()
read method '@consistent
Outer3::Bool terminate()
mut method
Void #next() exception Void (
  Void unused=(
    Outer0::that() ::#next() x=this.that().#next()
    Void unused0=(
      Outer2::Libs6::CellEnd x0=(
        Void unused2=return x
        catch return casted (
          on Outer2::Libs6::CellEnd casted

          on Any exception void
          )
        error {'@stringU
        'CastT-Should be unreachable code
        }
        )
      catch exception unused3 (
        on Void void
        )
      (
        Void unused4=exception void
        void
        )
      )
    Void unused1=return this.that(that:x)
    void
    )
  catch return result (
    on Void result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
read method
Void #checkEnd() (
  Outer0::terminate() cond=this.terminate()
  (
    Void unused=cond.#checkTrue()
    catch exception unused0 (
      on Void void
      )
    this.that().#checkEnd()
    )
  )
read method
Library #inner() this.that().#inner()
read method
Void #close() void}}}
Refactor:{
Redirect:{
type method
Outer0 #add(Outer2::Path^that'@consistent
, type Any^to'@consistent
)
mut method '@consistent
Outer2::Path #that()
read method '@consistent
Outer2::Path that()
mut method '@consistent
type Any #to()
read method '@consistent
type Any to()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::InvalidOnTopLevel, Outer2::SourceUnfit, Outer2::MethodClash (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.redirect(that:that, src:this.that(), dest:this.to())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void (
                  Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                  'InvalidOnTopLevel
                  }))
                  (
                    Void unused3=cond0.#checkTrue()
                    catch exception unused4 (
                      on Void (
                        Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond1=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                        'MethodClash
                        }))
                        (
                          Void unused5=cond1.#checkTrue()
                          catch exception unused6 (
                            on Void (
                              Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond2=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                              'SourceUnfit
                              }))
                              (
                                Void unused7=cond2.#checkTrue()
                                catch exception unused8 (
                                  on Void error err
                                  )
                                exception Outer2::SourceUnfit.#apply(mirror:mirror)
                                )
                              )
                            )
                          exception Outer2::MethodClash.#apply(mirror:mirror)
                          )
                        )
                      )
                    exception Outer2::InvalidOnTopLevel.#apply(mirror:mirror)
                    )
                  )
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
RenameSelector:{
type method
Outer0 #add(Outer2::Selector^that'@consistent
, Outer2::Path^of'@consistent
, Outer2::Selector^to'@consistent
)
mut method '@consistent
Outer2::Selector #that()
read method '@consistent
Outer2::Selector that()
mut method '@consistent
Outer2::Path #of()
read method '@consistent
Outer2::Path of()
mut method '@consistent
Outer2::Selector #to()
read method '@consistent
Outer2::Selector to()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::MethodClash (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.renameMethod(that:that, path:this.of(), src:this.that(), dest:this.to())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void (
                  Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                  'MethodClash
                  }))
                  (
                    Void unused3=cond0.#checkTrue()
                    catch exception unused4 (
                      on Void error err
                      )
                    exception Outer2::MethodClash.#apply(mirror:mirror)
                    )
                  )
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
RenamePath:{
type method
Outer0 #add(Outer2::Path^that'@consistent
, Outer2::Path^to'@consistent
)
mut method '@consistent
Outer2::Path #that()
read method '@consistent
Outer2::Path that()
mut method '@consistent
Outer2::Path #to()
read method '@consistent
Outer2::Path to()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::MethodClash, Outer2::ClassClash (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.renameClass(that:that, src:this.that(), dest:this.to())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void (
                  Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                  'MethodClash
                  }))
                  (
                    Void unused3=cond0.#checkTrue()
                    catch exception unused4 (
                      on Void (
                        Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond1=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                        'ClassClash
                        }))
                        (
                          Void unused5=cond1.#checkTrue()
                          catch exception unused6 (
                            on Void error err
                            )
                          exception Outer2::ClassClash.#apply(mirror:mirror)
                          )
                        )
                      )
                    exception Outer2::MethodClash.#apply(mirror:mirror)
                    )
                  )
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
RemoveImplementationSelector:{
type method
Outer0 #add(Outer2::Selector^that'@consistent
, Outer2::Path^of'@consistent
)
mut method '@consistent
Outer2::Selector #that()
read method '@consistent
Outer2::Selector that()
mut method '@consistent
Outer2::Path #of()
read method '@consistent
Outer2::Path of()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.removeImplementation(that:that, path:this.of(), selector:this.that())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void error err
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
RemoveImplementationPath:{
type method
Outer0 #add(Outer2::Path^that'@consistent
)
mut method '@consistent
Outer2::Path #that()
read method '@consistent
Outer2::Path that()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::PrivacyCoupuled (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.removeImplementation(that:that, path:this.that())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void (
                  Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                  'PrivacyCoupuled
                  }))
                  (
                    Void unused3=cond0.#checkTrue()
                    catch exception unused4 (
                      on Void error err
                      )
                    exception Outer2::PrivacyCoupuled.#apply(mirror:mirror)
                    )
                  )
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
AddDocumentationSelector:{
type method
Outer0 #add(Outer2::Selector^that'@consistent
, Outer2::Path^of'@consistent
, Outer2::Doc^doc'@consistent
)
mut method '@consistent
Outer2::Selector #that()
read method '@consistent
Outer2::Selector that()
mut method '@consistent
Outer2::Path #of()
read method '@consistent
Outer2::Path of()
mut method '@consistent
Outer2::Doc #doc()
read method '@consistent
Outer2::Doc doc()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.addDocumentation(that:that, path:this.of(), selector:this.that(), doc:this.doc())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void error err
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
AddDocumentationPath:{
type method
Outer0 #add(Outer2::Path^that'@consistent
, Outer2::Doc^doc'@consistent
)
mut method '@consistent
Outer2::Path #that()
read method '@consistent
Outer2::Path that()
mut method '@consistent
Outer2::Doc #doc()
read method '@consistent
Outer2::Doc doc()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::InvalidOnTopLevel (
  Void unused=(
    Void unused0=return Outer2::SafeOperators.addDocumentation(that:that, path:this.that(), doc:this.doc())
    catch error err (
      on Library (
        Outer2::Introspection::MirrorLibrary::#apply(lib ) mirror=Outer2::Introspection::MirrorLibrary.#apply(lib:err)
        (
          Outer2::Introspection::#apply(that p ) x=Outer2::Introspection.#apply(that:mirror, p:Outer2::Path.#stringParser(that:{'@stringU
          'Kind
          }))
          (
            Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
            'TargetUnavailable
            }))
            (
              Void unused1=cond.#checkTrue()
              catch exception unused2 (
                on Void (
                  Outer2::Introspection::#apply(that p ) ::#equalequal(that ) cond0=x.#equalequal(that:Outer3::BNS::S.#stringParser(that:{'@stringU
                  'InvalidOnTopLevel
                  }))
                  (
                    Void unused3=cond0.#checkTrue()
                    catch exception unused4 (
                      on Void error err
                      )
                    exception Outer2::InvalidOnTopLevel.#apply(mirror:mirror)
                    )
                  )
                )
              exception Outer2::TargetUnavailable.#apply(mirror:mirror)
              )
            )
          )
        )
      )
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
MakePrivatePath:{
type method
Outer0 #add(Outer2::Path^that'@consistent
)
mut method '@consistent
Outer2::Path #that()
read method '@consistent
Outer2::Path that()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::InvalidOnTopLevel Outer1::AddDocumentationPath.#begin().#add(that:this.that(), doc:Outer2::Doc.#stringParser(that:{'@private
})).#end().#leftleft(that:that)
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
MakePrivateSelector:{
type method
Outer0 #add(Outer2::Selector^that'@consistent
, Outer2::Path^of'@consistent
)
mut method '@consistent
Outer2::Selector #that()
read method '@consistent
Outer2::Selector that()
mut method '@consistent
Outer2::Path #of()
read method '@consistent
Outer2::Path of()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::InvalidOnTopLevel Outer1::AddDocumentationSelector.#begin().#add(that:this.that(), of:this.of(), doc:Outer2::Doc.#stringParser(that:{'@private
})).#end().#leftleft(that:that)
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
ExposeAsLibrary:{
Varres1:{
type method
mut Outer0 #apply(Library^inner'@consistent
)
mut method '@consistent
Void inner(Library that)
mut method '@consistent
Library #inner()
read method '@consistent
Library inner() }
type method
Outer0 #add(Outer2::Path^that'@consistent
)
mut method '@consistent
Outer2::Path #that()
read method '@consistent
Outer2::Path that()
method
Library #leftleft(Library that) exception Outer2::TargetUnavailable, Outer2::PrivacyCoupuled, Outer2::NSBMissing (
  Void unused=(
    Void unused0=(
      Outer0::that() ::#equalequal(that ) cond=this.that().#equalequal(that:Outer2::Path.#stringParser(that:{'@stringU
      'Outer0
      }))
      (
        Void unused3=cond.#checkTrue()
        catch exception unused4 (
          on Void void
          )
        return that
        )
      )
    Void unused1=this.checkTargetExists(that:that)
    Library res=that
    mut Outer0::Varres1 varres=Outer0::Varres1.#apply(inner:res)
    Void unused2=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
    'Outer0
    }), to:Outer2::Path.#stringParser(that:{'@stringU
    'PrImpl
    })).#end().#leftleft(that:varres.#inner()))
    catch exception unused5 (
      on Outer2::Report error Outer3::BNS::S.#stringParser(that:{'@stringU
      'CodeNotReachable
      })
      )
    (
      Void unused6=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
      'PrImpl::N
      }), to:Outer2::Path.#stringParser(that:{'@stringU
      'BNS::N
      })).#end().#leftleft(that:varres.#inner()))
      Void unused7=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
      'PrImpl::S
      }), to:Outer2::Path.#stringParser(that:{'@stringU
      'BNS::S
      })).#end().#leftleft(that:varres.#inner()))
      Void unused8=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
      'PrImpl::Bool
      }), to:Outer2::Path.#stringParser(that:{'@stringU
      'BNS::Bool
      })).#end().#leftleft(that:varres.#inner()))
      Void unused9=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
      'PrImpl::Debug
      }), to:Outer2::Path.#stringParser(that:{'@stringU
      'BNS::Debug
      })).#end().#leftleft(that:varres.#inner()))
      catch exception r (
        on Outer2::Report exception Outer2::NSBMissing.#apply(report:r)
        )
      (
        Void unused10=varres.inner(that:Outer1::RemoveImplementationPath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
        'BNS
        })).#end().#leftleft(that:varres.#inner()))
        catch exception unused11 (
          on Outer2::TargetUnavailable error Outer3::BNS::S.#stringParser(that:{'@stringU
          'CodeNotReachable
          })
          )
        (
          Void unused12=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
          'PrImpl
          }).#plusplus(that:this.that()), to:Outer2::Path.#stringParser(that:{'@stringU
          'Outer0
          })).#end().#leftleft(that:varres.#inner()))
          Void unused13=varres.inner(that:Outer1::RenamePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
          'BNS
          }), to:Outer2::Path.#stringParser(that:{'@stringU
          'Outer0
          })).#end().#leftleft(that:varres.#inner()))
          Void unused14=varres.inner(that:Outer1::MakePrivatePath.#begin().#add(that:Outer2::Path.#stringParser(that:{'@stringU
          'PrImpl
          })).#end().#leftleft(that:varres.#inner()))
          catch exception unused15 (
            on Outer2::Report error Outer3::BNS::S.#stringParser(that:{'@stringU
            'CodeNotReachable
            })
            )
          (
            Void unused16=return varres.#inner()
            void
            )
          )
        )
      )
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Void checkTargetExists(Library that) exception Outer2::TargetUnavailable (
  Outer1::AddDocumentationPath::#begin() ::#add(that doc ) ::#end() ::#leftleft(that ) unused=Outer1::AddDocumentationPath.#begin().#add(that:this.that(), doc:Outer2::Doc.#stringParser(that:{'@stringU
  'JustToSeeIfItIsThere
  })).#end().#leftleft(that:that)
  catch exception x (
    on Outer2::InvalidOnTopLevel error Outer3::BNS::S.#stringParser(that:{'@stringU
    'CodeNotReachable
    })
    )
  void
  )
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this}
Squares6:'@private
{
type method
Library #leftleft(Library that) exception Outer2::MethodClash, Outer2::ClassClash Outer2::Compose.#begin().#add(that:that).#end().#leftleft(that:{
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this})}}
Load:{
type method
Outer0 #begin()
method
Outer0 #end() this
method
Library #leftleft(Library that) exception Outer1::TargetUnavailable, Outer1::InvalidOnTopLevel, Outer1::SourceUnfit, Outer1::MethodClash (
  Void unused=('assume that have abstract Bool N and S

    Void unused0=return Outer1::Refactor::Redirect.#begin().#add(that:Outer1::Path.#stringParser(that:{'@stringU
    'Debug
    }), to:Outer1::Debug).#end().#leftleft(that:Outer1::Refactor::Redirect.#begin().#add(that:Outer1::Path.#stringParser(that:{'@stringU
    'S
    }), to:Outer2::BNS::S).#end().#leftleft(that:that))
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )}
Optimize:{
RemoveUnreachableCode:{
type method
Outer0 #apply()
type method
Outer0 #begin() Outer0.#apply()
method
Outer0 #end() this
method
Library #leftleft(Library that) using Outer2::SafeOperators check removeUnreachableCode(that:that) error Outer2::SafeOperators.mkError6(that6:Outer3::BNS::S.#stringParser(that:{'@stringU
'removeUnreachableCode
}))}}
DeployTowel:{
type method
Outer0 #apply(Library^fileName'@consistent
)
mut method '@consistent
Library #fileName()
read method '@consistent
Library fileName()
type method
type Outer0 #begin() Outer0
type method
Outer0 #add(Library fileName) Outer0.#apply(fileName:fileName)
method
Outer0 #end() this
method
Library #leftleft(Library that) (
  Library report=using Outer0::SafeOperatorsAccess13 check introspectionGiveInfo(that:that, path:Outer0.#stringParser(that:{'@stringU
  'Outer0
  })) error Outer0.#stringParser(that:{'@stringU
  'introspectionGiveInfo
  })
  Library text=using Outer0::SafeOperatorsAccess13 check introspectionDocAsString(that:report, path:Outer0.#stringParser(that:{'@stringU
  'AllAsString
  }), annotationN:Outer0.#numberParser(that:{'@stringU
  '0
  })) error Outer0.#stringParser(that:{'@stringU
  'introspectionDocAsString
  })
  Void unused=using Outer0::Alu6 check fileDebug(fileName:this.fileName(), content:text) error Outer0.#stringParser(that:{'@stringU
  'fileDebug
  })
  Outer1::ExitCode.normal()
  )
type method
Library #stringParser(Library that) that
type method
Library #numberParser(Library that) using Outer0::Alu6 check stringToInt32(that:that) error Outer0.#stringParser(that:{'@stringU
'stringToInt32
})
SafeOperatorsAccess13:'@private
{'@plugin
'L42.is/connected/withSafeOperators
}
Alu6:'@private
{'@plugin
'L42.is/connected/withAlu
}}
DeployLibrary:{
Varres2:{
type method
mut Outer0 #apply(Library^inner'@consistent
)
mut method '@consistent
Void inner(Library that)
mut method '@consistent
Library #inner()
read method '@consistent
Library inner() }
type method
Outer0 #apply(Outer1::Path^path'@consistent
, Outer1::URL^url'@consistent
)
mut method '@consistent
Outer1::Path #path()
read method '@consistent
Outer1::Path path()
mut method '@consistent
Outer1::URL #url()
read method '@consistent
Outer1::URL url()
type method
type Outer0 #begin() Outer0
method
Outer0 #end() this
type method
Outer0 #add(Outer1::Path that, Outer1::URL url) Outer0.#apply(path:that, url:url)
method
Library #leftleft(Library that) exception Outer1::TargetUnavailable, Outer1::PrivacyCoupuled, Outer1::NSBMissing (
  Void unused=(
    Library res=that
    mut Outer0::Varres2 varres=Outer0::Varres2.#apply(inner:res)
    Void unused0=varres.inner(that:Outer1::Refactor::ExposeAsLibrary.#begin().#add(that:this.path()).#end().#leftleft(that:varres.#inner()))
    Void unused1=varres.inner(that:Outer1::Optimize::RemoveUnreachableCode.#begin().#end().#leftleft(that:varres.#inner()))
    Void unused2=return Outer1::DeployTowel.#begin().#add(fileName:this.url().binaryRepr()).#end().#leftleft(that:varres.#inner())
    void
    )
  catch return result (
    on Library result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )}
MyLib:{'could be in another file

type method
Void printHelloWorld() Outer1::Debug.#apply(that:Outer2::BNS::S.#stringParser(that:{'@stringU
'Hello World Deployed
}))}}
BNS:{
N:{<:Outer2::PrImpl::S::ToS
type method
Outer0 #apply(Library^_binaryRepr'@consistent
)
mut method '@consistent
Library #_binaryRepr()
read method '@consistent
Library _binaryRepr()
method
Library binaryRepr() this._binaryRepr()
type method
Outer0 #numberParser(Library that) Outer0.#apply(_binaryRepr:using Outer2::PrImpl::Alu check stringToInt32(that:that) error void)
method toS() Outer1::S.#stringParser(that:using Outer2::PrImpl::Alu check int32ToString(that:this.binaryRepr()) error void)
method
Outer0 #plus(Outer0 that) Outer0.#apply(_binaryRepr:using Outer2::PrImpl::Alu check sumInt32(n1:this.binaryRepr(), n2:that.binaryRepr()) error void)
method
Outer0 #less(Outer0 that) Outer0.#apply(_binaryRepr:using Outer2::PrImpl::Alu check subInt32(n1:this.binaryRepr(), n2:that.binaryRepr()) error void)
method
Outer0 #times(Outer0 that) Outer0.#apply(_binaryRepr:using Outer2::PrImpl::Alu check mulInt32(n1:this.binaryRepr(), n2:that.binaryRepr()) error void)
method
Outer0 #divide(Outer0 that) Outer0.#apply(_binaryRepr:using Outer2::PrImpl::Alu check divInt32(n1:this.binaryRepr(), n2:that.binaryRepr()) error void)
method
Outer2::PrImpl::Bool #equalequal(Outer0 that) (
  Void unused=(
    Void unused0=using Outer2::PrImpl::Alu check ifInt32EqualDo(n1:this.binaryRepr(), n2:that.binaryRepr()) return Outer2::PrImpl::Bool.true()
    Void unused1=return Outer2::PrImpl::Bool.false()
    void
    )
  catch return result (
    on Outer2::PrImpl::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer2::PrImpl::Bool #left(Outer0 that) (
  Void unused=(
    Void unused0=using Outer2::PrImpl::Alu check ifInt32GrtDo(n1:this.binaryRepr(), n2:that.binaryRepr()) return Outer2::PrImpl::Bool.true()
    Void unused1=return Outer2::PrImpl::Bool.false()
    void
    )
  catch return result (
    on Outer2::PrImpl::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer2::PrImpl::Bool #leftequal(Outer0 that) (
  Void unused=(
    Void unused0=using Outer2::PrImpl::Alu check ifInt32GEqDo(n1:this.binaryRepr(), n2:that.binaryRepr()) return Outer2::PrImpl::Bool.true()
    Void unused1=return Outer2::PrImpl::Bool.false()
    void
    )
  catch return result (
    on Outer2::PrImpl::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer2::PrImpl::Bool #bangequal(Outer0 that) this.#equalequal(that:that).#bang()
method
Outer2::PrImpl::Bool #right(Outer0 that) this.#leftequal(that:that).#bang()
method
Outer2::PrImpl::Bool #rightequal(Outer0 that) this.#left(that:that).#bang()}
S:{<:Outer0::ToS
type method
Outer0 #stringParser(Library that) Outer0._private30(_binaryRepr30:that)
method
Library binaryRepr() this._binaryRepr30()
ToS:{interface
method
Outer1 toS() }
method toS() this
method
Outer0 #plusplus(Outer0::ToS that) Outer0.#stringParser(that:using Outer2::PrImpl::Alu check stringConcat(s1:this.binaryRepr(), s2:that.toS().binaryRepr()) error void)
method
Outer2::PrImpl::Bool #bangequal(Outer0 that) this.#equalequal(that:that).#bang()
method
Outer2::PrImpl::Bool #equalequal(Outer0 that) (
  Void unused=(
    Void unused0=using Outer2::PrImpl::Alu check ifStringEqualDo(s1:this.binaryRepr(), s2:that.binaryRepr()) return Outer2::PrImpl::Bool.true()
    Void unused1=return Outer2::PrImpl::Bool.false()
    void
    )
  catch return result (
    on Outer2::PrImpl::Bool result
    )
  error {'@stringU
  'CurlyBlock-Should be unreachable code
  }
  )
method
Outer0 #apply(Outer1::N that) Outer0.#stringParser(that:using Outer2::PrImpl::Alu check stringCharAt(that:this.binaryRepr(), pos:that.binaryRepr()) error void)
method
Outer1::N size() Outer1::N.#apply(_binaryRepr:using Outer2::PrImpl::Alu check stringSize(that:this.binaryRepr()) error void)
type method
Outer0 doubleQuote() Outer0.#stringParser(that:{'@stringU
'"\u000a
}).#apply(that:Outer1::N.#numberParser(that:{'@stringU
'0
}))
method
Outer0 replace(Outer0 that, Outer0 into) Outer0.#stringParser(that:using Outer2::PrImpl::Alu check stringReplace(that:this.binaryRepr(), src:that.binaryRepr(), dest:into.binaryRepr()) error void)
type method '@private
Outer0 _private30(Library^_binaryRepr30'@consistent
)
mut method '@consistent
Library #_binaryRepr30()
read method '@consistent
Library _binaryRepr30() }}}





*/