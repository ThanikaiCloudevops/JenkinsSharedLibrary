def call(Map params = [:]) {
    def service = params.get('service', '')
    if (!service) {
        error "'service' parameter is required"
    }
    def branchOverride = params.get('branch', '')

    def yamlFile = 'microservices/fsp.yml'

    def services = readYaml file: yamlFile

    echo "name of this : ${services}"

    echo "No config found for service '${service}' in ${yamlFile}"

    def config = services[service]
    if (!config) {
        error "No config found for service '${service}' in ${yamlFile}"
    }

    def repoUrl = config.repourl
    def credentialsId = config.credentialsId
    def branch = branchOverride ?: config.branch ?: 'development'

    if (!repoUrl || !credentialsId) {
        error "Missing repoUrl or credentialsId for service '${service}'"
    }

    echo "Cloning ${repoUrl} → into folder '${service}' on branch '${branch}'"

    dir(service) {
        checkout([
            $class: 'GitSCM',
            branches: [[name: "refs/heads/${branch}"]],
            doGenerateSubmoduleConfigurations: false,
            extensions: [
                [$class: 'CloneOption', depth: 1, noTags: false, shallow: true],
                [$class: 'RelativeTargetDirectory', relativeTargetDir: '.'] // <--- critical line
            ],
            userRemoteConfigs: [[
                url: repoUrl,
                credentialsId: credentialsId
            ]]
        ])
    }

    echo "Checkout of ${service} complete."
}
