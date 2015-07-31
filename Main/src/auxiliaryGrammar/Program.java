package auxiliaryGrammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import tools.Assertions;
import coreVisitors.Exists;
import coreVisitors.From;
import facade.Configuration;
import ast.Ast.MethodType;
import ast.Ast.NormType;
import ast.Ast.Path;
import ast.Ast.SignalKind;
import ast.ErrorMessage;
import ast.ExpCore;
import ast.ExpCore.Block.Dec;
import ast.ExpCore.ClassB;
import ast.ExpCore.ClassB.NestedClass;
import ast.ExpCore.Signal;
import ast.ExpCore._void;
import ast.ExpCore.ClassB.*;
import ast.Ast.*;

public class Program {
  private final Program next;
  private final ClassB cb;//cb is like the source, with no walkby
  private final ClassB ct;//ct is the annotated one, with collected interface types and walkby
  //nullable cb or ct, not both
  private Program(Program next,ClassB cb,ClassB ct){
    assert this.getClass()!=Program.class || (next!=null  && (cb!=null || ct!=null));
    this.next=next;this.cb=cb;this.ct=ct;
    }
  public ClassB getCt(int num){
    //assert this.classB!=null;
    assert !this.isEmpty():
      "empty program reached";
    if(num==0){return this.ct;}
    return this.next.getCt(num-1);
    }
  public ClassB getCb(int num){
    //assert this.classB!=null;
    assert !this.isEmpty():
      "empty program reached";
    if(num==0){return this.cb;}
    return this.next.getCb(num-1);
    }

  private static final Program regularEmpty=new Program(null,null,null){ };
  private static final Program executableStarEmpty=new Program(null,null,null){};

  public static Program empty(){return regularEmpty;}
  public boolean isExecutableStar(){
    if (this.isEmpty()){return this==executableStarEmpty;}
    return this.pop().isExecutableStar();
  }
  public Program getExecutableStar(){
    assert this!=executableStarEmpty;//may be not needed
    if (this==regularEmpty){return executableStarEmpty;}
    return new Program(this.pop().getExecutableStar(),this.topCb(),this.topCt());
  }
  public Program removeExecutableStar() {
    assert this!=regularEmpty;//may be not needed
    if (this==executableStarEmpty){return regularEmpty;}
    return new Program(this.pop().removeExecutableStar(),this.topCb(),this.topCt());
  }

  public Stage getStage(){
    if(ct==null){Assertions.codeNotReachable();}
    return ct.getStage();
    }
  public Stage getStage(Path p){return this.extractCt(p).getStage();};
  //public void __addAtTop(ClassB cb){this.inner.add(0,cb);}

  public Program addAtTop(ClassB cb,ClassB ct){return new Program(this,cb,ct);}
  public Program pop(){assert !this.isEmpty();return this.next;}
  public Program pop(int n){
    assert n>=0;
    if(n==0){return this;}
    return this.pop().pop(n-1);
    }
  public boolean isEmpty(){return this.next==null;}

  public ClassB topCb(){
    assert this.cb!=null;
    return this.cb;
    }
  public ClassB topCt(){
    assert this.ct!=null;
    return this.ct;
    }

  public boolean executablePlus(){
    assert !this.isEmpty();
    return this.ct.getStage()!=Stage.Less;
  }

  public boolean executablePlus(Path p){
    return this.extractCt(p).getStage()!=Stage.Less;
  }
  public boolean executable(Path p){
    return this.extractCt(p).getStage()==Stage.None;
  }

/*
    public Program collapse(int n){
    if(n==0){return this;}
    return this.collapseOne().collapse(n-1);
  }
  public Program collapseOne(){
    ClassB cb=this.top();
    Program result=this.next;
    ClassB cbNext=replaceWalkByWith(result.top(),cb);
    result=result.next.addAtTop(cbNext);
    return result;
  }*/
  public Program navigateInTo(String c){
    assert !this.isEmpty();
    Optional<Member> mOpt=getIfInDom(this.topCt().getMs(),c);
    if(!mOpt.isPresent()){
      throw new ErrorMessage.PathNonExistant(Arrays.asList(c),this.topCt());
    }
    Member m=mOpt.get();
    ClassB newTop=this.topCt().withMember(m.withBody(new ExpCore.WalkBy()));
    Program result=this.next.addAtTop(null,newTop);
    return result.addAtTop(null,(ClassB)((NestedClass)m).getInner());
  }
  public Program navigateInTo(List<String> paths){
    if(paths.isEmpty()){return this;}
    return this.navigateInTo(paths.get(0)).navigateInTo(paths.subList(1,paths.size()));
    }

  public ClassB extractCb(Path path){
    ClassB cb=this.getCb(path.outerNumber());
    cb = extractCBar(path.getCBar(), cb);
    assert cb!=null;
    return cb;
  }
  public ClassB extractCt(Path path){
    ClassB ct=this.getCt(path.outerNumber());
    ct = extractCBar(path.getCBar(), ct);
    assert ct!=null;
    return ct;
  }
  private static final Doc[] _trashCommentRef=new Doc[]{Doc.empty()};
  private static final Boolean[] _trashIsPrivateRef=new Boolean[]{false};
  public static ClassB extractCBar(List<String> list, ClassB cb) {
    return extractCBar(list, cb,_trashCommentRef,_trashIsPrivateRef);
  }
  public static ClassB extractCBar(List<String> list, ClassB cb,Doc[] commentRef) {
    return extractCBar(list, cb,commentRef,_trashIsPrivateRef);
  }
  public static ClassB extractCBar(List<String> list, ClassB cb,Boolean[] isPrivateRef) {
    return extractCBar(list, cb,_trashCommentRef,isPrivateRef);
  }
  public static ClassB extractCBar(List<String> list, ClassB cb,Doc[] commentRef,Boolean[]isPrivateRef) {
    assert cb!=null;
    for(String s:list){
      Optional<Member> optNc = Program.getIfInDom(cb.getMs(),s);
      if(!optNc.isPresent()){
        throw new ErrorMessage.PathNonExistant(list,cb);
        }
      NestedClass nc=(NestedClass)optNc.get();
      ExpCore ec=nc.getInner();
      commentRef[0]=nc.getDoc();
      isPrivateRef[0]|=nc.getDoc().isPrivate();
      if(ec instanceof ExpCore.WalkBy){
        throw new ErrorMessage.ProgramExtractOnWalkBy(null,new ArrayList<>(Arrays.asList(cb)));
        }
      if(ec instanceof ClassB){cb=(ClassB)nc.getInner();}
      else {
        throw new ErrorMessage.ProgramExtractOnMetaExpression(new Path(list),Collections.singletonList(cb));
        }
    }
    return cb;
  }
  public MethodWithType method(Path path,MethodSelector ms,boolean isOnlyType){
    if(path.isPrimitive()){
      throw new ErrorMessage.MethodNotPresent(path,ms,this.getInnerData());
      }
    ClassB classB=extractCt(path);
//    path=Path.parse("Outer0::C::C");
    //classB=(ClassB)From.from(classB,path);
    Optional<Member> result = getIfInDom(classB.getMs(),ms);
    if(!result.isPresent()){
      throw new ErrorMessage.MethodNotPresent(path,ms,this.getInnerData());
      }
    MethodWithType mwt=(MethodWithType)result.get();
    mwt=From.from(mwt, path);
    mwt=Norm.of(this,mwt,isOnlyType);
    return mwt;

  }

  public static Optional<NestedClass> findWalkBy(ClassB classB) {
    for(Member m:classB.getMs()){
      if(m.match(nc->nc.getInner() instanceof ExpCore.WalkBy, c2->false, c3->false)){
        return Optional.of(((NestedClass)m));
      }
    }
    return Optional.empty();
  }
  public static Optional<Member> getIfInDom(List<ExpCore.ClassB.Member> map, String elem){
    for(ExpCore.ClassB.Member m: map){
      if(m.match(nc->nc.getName().equals(elem), mi->false, mt->false)){return Optional.of(m);}
      }
    return Optional.empty();
  }
  public static Optional<Member> getIfInDom(List<ExpCore.ClassB.Member> map, ast.Ast.MethodSelector elem){
    //remember: the are no docs to make method selectors different.
    for(ExpCore.ClassB.Member m: map){
      if(m.match(nc->false,mi->mi.getS().equals(elem) ,mt->mt.getMs().equals(elem))){return Optional.of(m);}
      }
    return Optional.empty();
  }
  public static Optional<Member> getIfInDom(List<ExpCore.ClassB.Member> map, ExpCore.ClassB.Member elem){
    return elem.match(nc->getIfInDom(map,nc.getName()), mi->getIfInDom(map,mi.getS()),mt->getIfInDom(map,mt.getMs()));
  }
  public static void removeIfInDom(List<Member> ms,MethodSelector sel){
    for(Member memi:ms){
      boolean res=memi.match(
          nc->false,
          mi->{if(mi.getS().equals(sel)){ms.remove(mi);return true;}return false;},
          mt->{if(mt.getMs().equals(sel)){ms.remove(mt);return true;}return false;});
      if(res){break;}
    }
  }
  public static void removeIfInDom(List<Member> ms,String sel){
    for(Member memi:ms){
      boolean res=memi.match(
          nc->{if(nc.getName().equals(sel)){ms.remove(nc);return true;}return false;},
          mi->false,
          mt->false);
      if(res){break;}
    }
  }
  public static void replaceIfInDom(List<Member> ms,Member m){
    Object matchRes=m.match(
        nc->{for(Member mi:ms){if (!(mi instanceof NestedClass)){continue;}
          if (!nc.getName().equals(((NestedClass)mi).getName())){continue;}
          return ms.set(ms.indexOf(mi), m);//swap to keep order
          }return null;},
        mImpl->{for(Member mi:ms){if (!(mi instanceof MethodImplemented)){continue;}
          if (!mImpl.getS().equals(((MethodImplemented)mi).getS())){continue;}
          return ms.set(ms.indexOf(mi), m);//swap to keep order
          }return null;},
        mt->{for(Member mi:ms){if (!(mi instanceof MethodWithType)){continue;}
          if (!mt.getMs().equals(((MethodWithType)mi).getMs())){continue;}
          return ms.set(ms.indexOf(mi), m);//swap to keep order
          }return null;});
    if(matchRes==null){ms.add(m);}
  }


  public List<ExpCore.ClassB> getInnerData() {
    List<ExpCore.ClassB> result=new ArrayList<ExpCore.ClassB>();
    Program p=this;
    while(p.next!=null){result.add(p.ct);p=p.next;}
    return result;
  }

  public boolean isNotClassB(Path path) {
    assert !path.isPrimitive():"method isNotClassB is not defined over primitive paths";
    try{//like extract but no normalize
      ClassB cb=this.getCt(path.outerNumber());
      cb = extractCBar(path.getCBar(), cb);
      }
    catch(ErrorMessage.ProgramExtractOnMetaExpression found){return true;}
    catch(ErrorMessage.ProgramExtractOnWalkBy found){return true;}
    return false;
  }
  public boolean checkFullyNormalized(){
    if(this.isEmpty()){return true;}
    checkFullyNormalized(this.topCt());
    return this.pop().checkFullyNormalized();
  }
  public static ClassB replaceWalkByWith(ClassB cb, ExpCore newExp) {
    ClassB ct=cb;
    Optional<NestedClass> opt = findWalkBy(ct);
    if(opt.isPresent()){
      ct=ct.withMember(opt.get().withBody(newExp));
    }
    return ct;
  }
  private static boolean checkFullyNormalized(ClassB cb) {
    for(Member m:cb.getMs()){
      m.match(
        nc->checkFullyNormalized((ClassB)nc.getInner()),
        mi->{throw Assertions.codeNotReachable();},
        mt->checkFullyNormalized(mt)
        );
      }
    return true;
  }
  private static boolean checkFullyNormalized(MethodWithType mt) {
    checkFullyNormalized(mt.getMt());
    if(!mt.getInner().isPresent()){return true;}
    ExpCore e=mt.getInner().get();
    return Exists.of(e, s->{
      if(!(s instanceof ExpCore.Block)){return false;}
      ExpCore.Block b=(ExpCore.Block)s;
      for( Dec d:b.getDecs()){
        assert d.getT() instanceof NormType;
      }
      if(!b.get_catch().isPresent()){return false;}
      for(ExpCore.Block.On on:b.get_catch().get().getOns()){
        assert on.getT()instanceof NormType;
      }
      return false;
    });
  }
  private static void checkFullyNormalized(MethodType mt) {
    assert mt.getReturnType() instanceof NormType;
    for(Type t:mt.getTs()){
      assert t instanceof NormType;
    }
  }
  public void exePlusOk(HashMap<String,NormType> varEnv){
    if(this.getStage()==Stage.Less){return;}
    for(NormType nt:varEnv.values()){
      if(nt.getPath().isPrimitive()){continue;}
      if(this.getStage(nt.getPath())!=Stage.Less){continue;}
      throw new ErrorMessage.PathNonStar(nt.getPath(),varEnv);
    }
  }
  public boolean checkComplete(){
    if(this.isEmpty()){return true;}
    if(this.topCt().getStage()!=Stage.Star){return false;}
    return this.pop().checkComplete();
  }


}
