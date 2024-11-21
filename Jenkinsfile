pipeline {
    agent any

    stages {

        stage('Start Selenium Grid') {
            steps {
                script {
                    // Detiene cualquier contenedor previo que pueda estar en conflicto
                    bat 'docker-compose down || echo "No containers to stop"'

                    // Inicia Selenium Grid
                    bat 'docker-compose up -d'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Verificar si los contenedores están ejecutándose antes de ejecutar pruebas
                    def seleniumHubRunning = bat(returnStatus: true, script: 'docker ps -q --filter "name=selenium-hub"') == 0
                    if (!seleniumHubRunning) {
                        error("Selenium Grid is not running. Aborting tests.")
                    }
                    // Ejecutar pruebas
                    bat './gradlew clean test'
                }
            }
        }

        stage('Stop Selenium Grid') {
            steps {
                script {
                    // Detener Selenium Grid al finalizar
                    bat 'docker-compose down'
                }
            }
        }
    }

    post {
        always {
            script {
                // Asegurarse de que existan los reportes antes de procesarlos
                def reportsExist = fileExists('build/test-results/test')
                if (reportsExist) {
                    junit 'build/test-results/test/*.xml' // Publicar reportes de JUnit
                    publishHTML(target: [
                        allowMissing: false,
                        keepAll: true,
                        reportDir: 'build/reports/tests/test',
                        reportFiles: 'index.html',
                        reportName: 'Test Report'
                    ])
                } else {
                    echo "No test reports found to publish."
                }
            }
        }

        failure {
            echo 'Pipeline failed. Check logs for more details.'
        }

        success {
            echo 'Pipeline executed successfully.'
        }
    }
}
