
package org.asura.csveditor.validation.checker;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import org.asura.csveditor.validation.ValidationError;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * Executes the given groovy as check
 */
public class GroovyValidation extends EmptyValueIsValid {

    private String groovyScript;
    private Script script;

    public GroovyValidation(String groovyScript) {
        this.groovyScript = groovyScript;
        GroovyShell shell = new GroovyShell();
        script = shell.parse(groovyScript);
    }


    @Override
    public void check(int row, String value, ValidationError error) {
        Binding binding = new Binding();
        binding.setVariable("value", value);
        script.setBinding(binding);

        Object groovyResult = null;
        try {
            groovyResult = script.run();
        } catch (CompilationFailedException e) {
            error.add("validation.message.groovy.exception", groovyScript, e.getMessage());
            e.printStackTrace();
        }
        if (groovyResult == null) {
            error.add("validation.message.groovy.return.null", groovyScript);
        }

        if (!isScriptResultTrue(groovyResult)) {
            error.add(groovyResult.toString());
        }
    }

    @Override
    public Type getType() {
        return Type.GROOVY;
    }

    private boolean isScriptResultTrue(Object groovyResult) {
        return groovyResult.equals(true) || groovyResult.toString().trim().toLowerCase().equals("true");
    }
}
