package com.epam.jobizer.plugin


class PipelineReaderTest extends GroovyTestCase {

    void testShouldReadDslFromFile() {
        Script dslScript = new GroovyShell().parse(new File(getClass().getResource("/.pipeline").getFile()).text)
        dslScript.metaClass = PipelineDsl.createEMC(dslScript.class, {
            ExpandoMetaClass emc ->
                emc.createJobs = {
                    Closure cl ->
                    cl.delegate = new PipelineDsl(new FlowInfo())
                    cl()
                }
        })
        dslScript.run()
    }


}
