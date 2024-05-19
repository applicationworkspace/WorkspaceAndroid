pipeline {
  agent {
    label 'built-in'
  }
  options {
    skipStagesAfterUnstable()
  }
  stages {
    stage('Compile') {
      steps {
        sh './gradlew compileDebugSources'
      }
    }
    stage('Unit test') {
      steps {
        sh './gradlew testDebugUnitTest'
        junit '**/TEST-*.xml'
      }
    }
    stage('Build debug APK') {
      steps {
        sh './gradlew assembleDebug'
        archiveArtifacts '**/*.apk'
      }
    }
    stage('Static analysis') {
      steps {
        sh './gradlew lintDebug'
        androidLintParser pattern: '**/lint-results-*.xml'
      }
    }
    stage('get_commit_msg') {
        steps {
            script {
                env.GIT_COMMIT_MSG = sh (script: 'git log -1 --pretty=%B ${GIT_COMMIT}', returnStdout: true).trim()
            }
        }
    }

    stage('Deploy distribution') {
       when {
          branch 'develop'
       }
       steps {
         echo "Uploading to app distribution"
         withCredentials([string(credentialsId: 'firebase-android-app-id', variable: 'FIREBASE_ANDROID_APP_ID'), string(credentialsId: 'firebase-distribution-token', variable: 'FIREBASE_DISTRIBUTION_TOKEN')]) {
            sh 'firebase appdistribution:distribute $WORKSPACE/app/build/outputs/apk/debug/app-debug.apk --app $FIREBASE_ANDROID_APP_ID --token $FIREBASE_DISTRIBUTION_TOKEN --release-notes "${GIT_COMMIT_MSG}" --groups "app-testers"'
         }
       }
       post {
          success {
            withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
                slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#00ff00", message: "Distribution ready (${GIT_COMMIT_MSG})" )
            }
          }
        }
      }

  }

  post {
    failure {
        withCredentials([string(credentialsId: 'jenkins-slack-token', variable: 'SLACK_TOKEN')]) {
            slackSend( channel: "#pipeline_process", token: "${SLACK_TOKEN}", color: "#FF0000", message: "${custom_msg_error()}")
        }
    }
  }

}

def custom_msg_error()
    {
        def JENKINS_URL= "localhost:8080"
        def JOB_NAME = env.JOB_NAME
        def BUILD_ID = env.BUILD_ID
        def JENKINS_LOG = "Job [${env.JOB_NAME}] Logs path: ${JENKINS_URL}/job/${JOB_NAME}/${BUILD_ID}"
        return JENKINS_LOG
}

// def custom_msg_deploy_success()
// {
//     def MESSAGE_BODY = "              {
//                                         "blocks": [
//                                           {
//                                             "type": "header",
//                                             "text": {
//                                               "type": "plain_text",
//                                               "text": "CI/CD workflow (Jenkins)"
//                                             }
//                                           },
//                                           {
//                                             "type": "section",
//                                             "fields": [
//                                               {
//                                                 "type": "mrkdwn",
//                                                 "text": "*Type:*\nNew QA build"
//                                               }
//                                             ]
//                                           },
//                                           {
//                                             "type": "divider"
//                                           },
//                                           {
//                                             "type": "section",
//                                             "text": {
//                                               "type": "mrkdwn",
//                                               "text": "*Details:*\n$ Git message here"
//                                             }
//                                           }
//                                         ]
//                                       }
//                                       "
// }