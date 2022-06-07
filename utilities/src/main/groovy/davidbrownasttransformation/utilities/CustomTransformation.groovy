package davidbrownasttransformation.utilities

import grails.gorm.DetachedCriteria
import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import java.lang.reflect.Modifier

@GroovyASTTransformation
@CompileStatic
class CustomTransformation implements ASTTransformation {
    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        // Forgoing all error handling...

        ClassNode classNodeToTransform = nodes[1] as ClassNode;
        def detachedCriteraConstructorArgs = new ArgumentListExpression();

        detachedCriteraConstructorArgs.addExpression(new ClassExpression(classNodeToTransform.getPlainNodeReference()))

        def detachedCriteriaConstructor = new ConstructorCallExpression(new ClassNode(DetachedCriteria), detachedCriteraConstructorArgs)

        Expression isNullMethodArguments = new ArgumentListExpression(
                new ConstantExpression("dateDeleted"));

        def isNullMethodCall = new MethodCallExpression(detachedCriteriaConstructor, "isNull", isNullMethodArguments)

        def returnStatement = new ReturnStatement(new MethodCallExpression(isNullMethodCall, "list", new ArgumentListExpression()))

        def listNotDeletedMethodNode = new MethodNode("listNotDeleted",
                Modifier.PUBLIC | Modifier.STATIC,
                new ClassNode(List),
                new Parameter[0],
                null,
                returnStatement)

        classNodeToTransform.addMethod(listNotDeletedMethodNode);
    }
}
