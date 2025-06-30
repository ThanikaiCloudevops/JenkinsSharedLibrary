def call(Map params = [:]) {
    def service = params.get('service', '')
    if (!service) {
        error "'service' parameter is required for build step"
    }

    dir(service) {
        // if (!fileExists('package.json')) {
        //     error " package.json not found in '${service}' directory"
        // }

        // Use a persistent cache location (shared between builds)
        def npmCacheDir = "/var/jenkins_home/.npm_cache"

        // Ensure cache directory exists
        sh "mkdir -p ${npmCacheDir}"

        // Run npm install using the shared cache
        sh "npm config set cache ${npmCacheDir} --global"
        sh "npm install"

        // Build the project
        sh 'npm run build'
    }

    echo "'${service}' built successfully with npm cache."
}
