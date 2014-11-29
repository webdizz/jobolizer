package com.epam.jobizer.jobdsl.impl

import spock.lang.Specification

class DslExecutorTest extends Specification {

    def 'should execute DSL from file' (){
        when:
        Binding binding = new Binding();
        binding.setVariable("foo", new Integer(2));
        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate("println 'Hello World!'; x = 123; return foo * 10");
        then:
        true
    }
}
