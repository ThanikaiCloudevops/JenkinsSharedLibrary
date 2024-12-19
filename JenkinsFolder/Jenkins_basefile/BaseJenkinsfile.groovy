pipelineJob("Base Pipeline env structure") {
    description("This Job runs the base pipeline structure from a Git repo")

    definition {
        cpsScm {
            scm {
                git('https://github.com/thanikai-devops/Devops_ansible.git'){
                    branch ('dev-jenkins')
                }
            }
            scriptPath('jenkinsfile/jenkinsfile')  // Make sure this path is correct
        }
    }
}
