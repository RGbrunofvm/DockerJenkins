pipeline {
    agent any

    stages {

        stage('Clean Existing Containers') {
            steps {
                script {
                    echo 'Removing any existing containers with the name selenium-hub...'
                    // Forzar la eliminación del contenedor conflictivo si existe
                    bat 'docker rm -f selenium-hub || echo "No existing selenium-hub container to remove."'
                }
            }
        }

        stage('Start Selenium Grid') {
            steps {
                script {
                    echo 'Stopping any existing containers to avoid conflicts...'
                    bat 'docker-compose down || echo "No containers to stop."'

                    echo 'Starting Selenium Grid containers...'
                    bat 'docker-compose up -d'
                }
            }
        }

        stage('Verify Selenium Grid') {
            steps {
                script {
                    echo 'Checking if Selenium Grid is running...'
                    def seleniumHubRunning = bat(returnStatus: true, script: 'docker ps -q --filter "name=selenium-hub"') == 0
                    if (!seleniumHubRunning) {
                        error("Selenium Grid is not running. Aborting pipeline.")
                    }
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    echo 'Running tests with Gradle...'
                    bat './gradlew clean test'
                }
            }
        }

        stage('Stop Selenium Grid') {
            steps {
                script {
                    echo 'Stopping Selenium Grid containers...'
                    bat 'docker-compose down'
                }
            }
        }
    }
}
