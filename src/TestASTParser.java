/**
 * @author Joshua Schijns 10017168
 */
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;
import org.junit.Test;


public class TestASTParser {
	/**
	 * Checks that a parser can be constructed for JLS8
	 */
	@Test
	public void testCreateParserForJLS8() {
		assertNotNull(ASTParser.newParser(AST.JLS8));
	}

	/**
	 * Checks that a parser cannot be constructed for "0", a meaningless value for it
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateParserFor0(){
		assertNotNull(ASTParser.newParser(0));
	}
	
	/**
	 * Verifies the correct apilevel was established
	 */
	@Test
	public void testAPIlevel(){
		String sourceCode = "public class Bar {}";
		ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(sourceCode.toCharArray());
	    CompilationUnit cu = (CompilationUnit)parser.createAST(null);
	    cu.accept(new ASTVisitor() {});
		assertEquals(8,cu.getAST().apiLevel());
	}
	
	/**
	 * Verfies the node type created, should be COMPILATION_UNIT
	 */
	@Test
	public void testNodeType(){
		String sourceCode = "public class Bar {}";
		ASTParser parser = ASTParser.newParser(AST.JLS8);
	    parser.setKind(ASTParser.K_COMPILATION_UNIT);
	    parser.setSource(sourceCode.toCharArray());
	    CompilationUnit cu = (CompilationUnit)parser.createAST(null);
	    cu.accept(new ASTVisitor() {});
		assertEquals(ASTNode.COMPILATION_UNIT,cu.getNodeType());
	}
	
	/**
	 * Can't create an ast from no source code
	 */
	@Test(expected = IllegalStateException.class)
	public void test2(){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		assertNotNull(parser.createAST(null));
	}
	
	/**
	 * Checks that you cant set the kind to 10, a useless number
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetKindFor10(){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(10);
	}
	
	/**
	 * Checks for the proper parsing of two statements
	 */
	@Test
	public void testParseOfStatements(){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource("int num = 2; \n int sum = num+1;".toCharArray());
 
		parser.setKind(ASTParser.K_STATEMENTS);
 
		Block block = (Block) parser.createAST(null);
 
		block.accept(new ASTVisitor() {});
		String one = "int num=2;\n";
		String two = "int sum=num + 1;\n";

		assertEquals(one,block.statements().get(0).toString());
		assertEquals(two,block.statements().get(1).toString());
	}
	
	/**
	 * Checks that an environment can't be set when String[] encodings not null
	 */
	@Test(expected = IllegalArgumentException.class)
	public void test5(){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		String[] sourceCode = new String[3];
		parser.setEnvironment(null, null, sourceCode, false);
	}
	
	/**
	 * 
	 */
	@Test(expected = IllegalStateException.class)
	public void test6(){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		assertNotNull(parser.createAST(new NullProgressMonitor()));
	}
	
//	@Test(expected = IllegalStateException.class)
//	public void test8(){
//		String[] str1 = new String[1];
//		str1[0] = "Foo";
//		String[] str2 = new String[2];
//		String[] str3 = new String[3];
//		str2[0] = "Bar";
//		str2[1] = "Foo";
//		str3[0] = "Public";
//		str3[1] = "Class";
//		str3[2] = "{}";
//	    ASTParser parser = ASTParser.newParser(AST.JLS8);
//	    assertNotNull(parser.createASTs(str1, str2, str3, null, new NullProgressMonitor()));
//	    
//	}
}
