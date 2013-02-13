package org.furidamu.scalainstaller

import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity extends Activity with TypedActivity {
  def installClickListener: View.OnClickListener {def onClick(view: View): Unit} = {
    new View.OnClickListener {
      def onClick(view: View): Unit = {
        progress = ProgressDialog.show(MainActivity.this, "Installing...", "")
        val thread: Thread = new Thread(new Runnable {
          def run: Unit = {
            val installer: ScalaLibraryInstaller = new ScalaLibraryInstaller(getApplicationContext)
            installer.installScalaLibs()
            runOnUiThread(new Runnable {
              def run {
                installButton.setEnabled(false)
                unInstallButton.setEnabled(false)
                text.setTextSize(20)
                text.setText("Finished! Please restart your phone.")
                progress.dismiss()
              }
            })
          }
        })
        thread.start()
      }
    }
  }

  def uninstallClickListener: View.OnClickListener {def onClick(view: View): Unit} = {
    new View.OnClickListener {
      def onClick(view: View): Unit = {
        progress = ProgressDialog.show(MainActivity.this, "Uninstalling...", "")
        val thread: Thread = new Thread(new Runnable {
          def run: Unit = {
            val installer: ScalaLibraryInstaller = new ScalaLibraryInstaller(getApplicationContext)
            installer.uninstallScalaLibs()
            runOnUiThread(new Runnable {
              def run {
                installButton.setEnabled(false)
                unInstallButton.setEnabled(false)
                text.setTextSize(20)
                text.setText("Finished! Please restart your phone.")
                progress.dismiss()
              }
            })
          }

        })
        thread.start()
      }
    }
  }

  protected override def onCreate(savedInstanceState: Bundle): Unit = {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)

    installButton.setOnClickListener(installClickListener)
    unInstallButton.setOnClickListener(uninstallClickListener)
  }

  var progress: ProgressDialog = null
  lazy val text = findView(TR.text)
  lazy val installButton = findView(TR.install_button)
  lazy val unInstallButton = findView(TR.uninstall_button)

}

