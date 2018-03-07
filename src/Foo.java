import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Foo {
  public static void main(String[] args) {
    Foo foo = new Foo();
    foo.parse("public class Bar {}");
  }

  public Foo() {
  }

  public void parse(String sourceCode) {
    ASTParser parser = ASTParser.newParser(AST.JLS8);

    parser.setKind(ASTParser.K_COMPILATION_UNIT);
    parser.setSource(sourceCode.toCharArray());

    CompilationUnit cu = (CompilationUnit)parser.createAST(null);

    cu.accept(new ASTVisitor() {}); 
  }
}