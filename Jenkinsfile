pipeline {
  agent any
  tools { maven 'Maven' }

  stages {
    stage('Checkout') {
      steps { git branch: 'main', url: 'https://github.com/vsagar225225/NBA2.git' }
    }

    stage('Build & Test') {
      steps {
        // If tests fail, still continue to post actions:
        catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
          bat 'mvn clean test'
        }
      }
    }
  }

  post {
    always {
      // Publish cucumber JSON even if tests failed
      cucumber buildStatus: 'UNSTABLE',
        fileIncludePattern: '**/target/cucumber-report.json',
        sortingMethod: 'ALPHABETICAL'

      // If you don’t generate surefire XML, keep allowEmptyResults true
      junit testResults: '**/target/surefire-reports/*.xml', allowEmptyResults: true

      archiveArtifacts artifacts: '**/target/**', fingerprint: true
    }
  }
}
