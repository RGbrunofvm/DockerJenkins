pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git url: 'https://github.com/RGbrunofvm/JenkinsDocker.git', branch: 'main', credentialsId: 'your-credentials-id'
            }
        }

        stage('Start Selenium Grid') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew clean test'
            }
        }

        stage('Stop Selenium Grid') {
            steps {
                sh 'docker-compose down'
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml' // Publicar reportes de JUnit
            publishHTML(target: [
                allowMissing: true,
                keepAll: true,
                reportDir: 'build/reports/tests/test',
                reportFiles: 'index.html',
                reportName: 'Test Report'
            ])
        }
    }
}
