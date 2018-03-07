/**
 * @author Joshua Schijns 10017168
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.TagElement;

import org.junit.Test;

public class TestASTParserIntegration {
	CompilationUnit cu = null;
	ASTParser parser = null;
	/**
	 * Checks to make sure that bindings recovery was set to false as default
	 */
	@Test
	public void testBindingRecovery(){
		String sourceCode = "public class Bar {}";
		ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(sourceCode.toCharArray());
	    cu = (CompilationUnit)parser.createAST(null);
	    cu.accept(new ASTVisitor() {});	  
		assertFalse(cu.getAST().hasBindingsRecovery());	
	}
	
	/**
	 * Checks to make sure that resolved bindings was set to false as default
	 */
	@Test
	public void testResolvedBinding(){
		String sourceCode = "public class Bar {}";
		ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(sourceCode.toCharArray());
	    cu = (CompilationUnit)parser.createAST(null);
	    cu.accept(new ASTVisitor() {});
		assertFalse(cu.getAST().hasResolvedBindings());	
	}

	/**
	 * Checks if the node type for a K_STATEMENTS is Block
	 */
	@Test
	public void testStatementsNodeType(){
		String sourceCode = "if (true) int i = 1;";
		ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_STATEMENTS);
	    parser.setSource(sourceCode.toCharArray());
	    Statement st = (Statement)parser.createAST(null);
	    st.accept(new ASTVisitor() {});
	    assertEquals(ASTNode.BLOCK,st.getNodeType());
	}
	
}
