/* Copyright (C) 2012 Tim Boudreau

 Permission is hereby granted, free of charge, to any person obtaining a copy 
 of this software and associated documentation files (the "Software"), to 
 deal in the Software without restriction, including without limitation the 
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 sell copies of the Software, and to permit persons to whom the Software is 
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all 
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. */
package org.netbeans.modules.nodejs.libraries;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.netbeans.modules.nodejs.NodeJSProject;
import org.netbeans.modules.nodejs.Npm;
import org.netbeans.modules.nodejs.ui.UiUtil;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/**
 *
 * @author tim
 */
public class LibrariesPanel extends javax.swing.JPanel implements Runnable, DocumentListener {
    private final RequestProcessor rp = new RequestProcessor( "Node.js search", 1, true, true );
    private final RequestProcessor rp2 = new RequestProcessor( "npm output processor", 1, true, true );
    private final RequestProcessor.Task task;
    private final RequestProcessor.Task outTask;
    private final String defaultText = NbBundle.getMessage( LibrariesPanel.class, "LibrariesPanel.searchField.text" );

    /**
     * Creates new form LibrariesPanel
     */
    @SuppressWarnings ("LeakingThisInConstructor")
    public LibrariesPanel ( NodeJSProject project ) {
        initComponents();
        task = rp.create( this );
        searchField.getDocument().addDocumentListener( this );
        outTask = rp2.create( new OutProcessor() );
        UiUtil.prepareComponents( this );
        statusLabel.setText( " " );
        progress.setVisible( false );
        jScrollPane1.setAutoscrolls( false );
        inner.setAutoscrolls( false );
        jScrollPane1.getViewport().setAutoscrolls( false );
        jScrollPane1.getVerticalScrollBar().setUnitIncrement( 100 );
        jScrollPane1.getVerticalScrollBar().setBlockIncrement( 200 );
        searchField.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed ( KeyEvent e ) {
                searchField.setForeground( UIManager.getColor( "textText" ) );
                searchField.removeKeyListener( this );
            }
        } );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        statusLabel = new javax.swing.JLabel();
        searchLabel = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        inner = new javax.swing.JPanel();
        instructionsLabel = new javax.swing.JLabel();
        linkLabel = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();

        statusLabel.setText(org.openide.util.NbBundle.getMessage(LibrariesPanel.class, "LibrariesPanel.statusLabel.text")); // NOI18N

        searchLabel.setText(org.openide.util.NbBundle.getMessage(LibrariesPanel.class, "LibrariesPanel.searchLabel.text")); // NOI18N

        searchField.setForeground(javax.swing.UIManager.getDefaults().getColor("controlShadow"));
        searchField.setText(org.openide.util.NbBundle.getMessage(LibrariesPanel.class, "LibrariesPanel.searchField.text")); // NOI18N

        inner.setLayout(new javax.swing.BoxLayout(inner, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(inner);

        instructionsLabel.setText(org.openide.util.NbBundle.getMessage(LibrariesPanel.class, "LibrariesPanel.instructionsLabel.text")); // NOI18N

        linkLabel.setForeground(java.awt.Color.blue);
        linkLabel.setText(org.openide.util.NbBundle.getMessage(LibrariesPanel.class, "LibrariesPanel.linkLabel.text")); // NOI18N
        linkLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.blue));
        linkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showNpmDownloadInstructions(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(instructionsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(linkLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(searchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(statusLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(instructionsLabel)
                    .addComponent(linkLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchLabel)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statusLabel)
                .addGap(14, 14, 14))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {progress, searchField});

    }// </editor-fold>//GEN-END:initComponents

    private void showNpmDownloadInstructions(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showNpmDownloadInstructions
        try {
            URLDisplayer.getDefault().showURLExternal( new URL( "https://github.com/isaacs/npm#readme" ) );
        } catch ( MalformedURLException ex ) {
            Exceptions.printStackTrace( ex );
        }
    }//GEN-LAST:event_showNpmDownloadInstructions
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel inner;
    private javax.swing.JLabel instructionsLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel linkLabel;
    private javax.swing.JProgressBar progress;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel statusLabel;
    // End of variables declaration//GEN-END:variables
    AtomicBoolean cancelled;
    private final Object lock = new Object();
    private InputStream currProcessIn;

    @Override
    public void run () {
        Process cur;
        synchronized ( lock ) {
            cur = process;
        }
        if (cur != null) {
            cur.destroy();
            if (outProcessorThread != null) {
                outProcessorThread.interrupt();
            }
        }
        Document d = searchField.getDocument();
        final String[] txt = new String[1];
        d.render( new Runnable() {
            @Override
            public void run () {
                txt[0] = searchField.getText();
            }
        } );
        if (txt[0].trim().equals( "" )) {
            return;
        }
        ProcessBuilder pb = new ProcessBuilder( Npm.getDefault().exe(), "search", txt[0] ); //NOI18N
        try {
            Process p = pb.start();
            synchronized ( lock ) {
                currProcessIn = p.getInputStream();
                lock.notify();
            }
        } catch ( IOException ex ) {
            Exceptions.printStackTrace( ex );
        }
    }
    private volatile boolean visible = true;
    private Process process;

    @Override
    public void addNotify () {
        super.addNotify();
        visible = true;
        outTask.schedule( 300 );
    }

    @Override
    public void removeNotify () {
        visible = false;
        Process proc;
        synchronized ( lock ) {
            currProcessIn = null;
            lock.notify();
            if (outProcessorThread != null) {
                outProcessorThread.interrupt();
            }
            proc = process;
            outTask.cancel();
        }
        super.removeNotify();
        if (proc != null) {
            process.destroy();
        }
    }
    private Thread outProcessorThread;

    private class OutProcessor implements Runnable {
        @Override
        @SuppressWarnings ("CallToThreadYield")
        public void run () {
            //How this all works:
            //Any edit to the search text schedules the LibrariesPanel runnable
            //on a background thread.  That thread will start an external npm
            //process and notify this thread that there is something to do.
            //
            //This thread loops continuously and grabs whatever process
            //InputStream is available, and processes output line-by-line,
            //publishing to the event thread whenever a line is complleted,
            //until there is no more output or the process it has been slurping
            //data from is destroyed, at which point it goes back to 
            //lock.wait() until it is given something to do again.
            //
            //Hiding the UI will destroy any running processes and cause
            //both background threads to exit.
            synchronized ( lock ) {
                outProcessorThread = Thread.currentThread();
            }
            try {
                InputStream in = null;
                final Process p = process;
                main:
                for (;;) {
                    InputStream newIn;
                    synchronized ( lock ) {
                        newIn = currProcessIn;
                    }
                    if (newIn != null) {
                        StringBuilder sb = new StringBuilder();
                        try {
                            for (;;) {
                                int val = newIn.read();
                                if (Thread.interrupted()) {
                                    try {
                                        in.close();
                                    } finally {
                                        break main;
                                    }
                                }
                                if (val == -1) {
                                    if (sb.length() > 0) {
                                        publish( sb );
                                        sb = new StringBuilder();
                                        break;
                                    }
                                }
                                char c = (char) val;
                                if (c == '\n') { //NOI18N
                                    publish( sb );
                                    Thread.yield();
                                    if (Thread.interrupted()) {
                                        try {
                                            in.close();
                                        } finally {
                                            break main;
                                        }
                                    }
                                    sb = new StringBuilder();
                                } else {
                                    sb.append( c );
                                }
                            }
                        } catch ( IOException ioe ) {
                            Exceptions.printStackTrace( ioe );
                        } finally {
                            EventQueue.invokeLater( new Runnable() {
                                @Override
                                public void run () {
                                    synchronized ( lock ) {
                                        if (p != process) {
                                            return;
                                        }
                                    }
                                    statusLabel.setText( NbBundle.getMessage(
                                            LibrariesPanel.class, "SEARCH_DONE",
                                            inner.getComponentCount() ) ); //NOI18N
                                    progress.setIndeterminate( false );
                                    progress.setVisible( false );
                                }
                            } );
                        }
                    }
                    synchronized ( lock ) {
                        try {
                            lock.wait();
                        } catch ( InterruptedException ex ) {
                            Exceptions.printStackTrace( ex );
                        }
                        if (!visible) {
                            return;
                        }
                    }
                }
            } finally {
            }
        }
    }
    public static final Pattern p = Pattern.compile(
            "^(\\S+)\\s+(.*?)=(\\S+).*?$", Pattern.MULTILINE | Pattern.DOTALL ); //NOI18N

    private void publish ( CharSequence seq ) {
        final Matcher m = p.matcher( seq );
        Process p;
        synchronized ( lock ) {
            p = this.process;
        }
        final Process pp = p;
        if (m.lookingAt()) {
            EventQueue.invokeLater( new Runnable() {
                @Override
                public void run () {
                    synchronized ( lock ) {
                        if (pp != process) {
                            //stale runnable
                            return;
                        }
                    }
                    inner.add( new OneLibraryPanel( m.group( 1 ), m.group( 2 ), m.group( 3 ) ) );
                    inner.invalidate();
                    inner.revalidate();
                    inner.repaint();
                    statusLabel.setText( NbBundle.getMessage( LibrariesPanel.class,
                            "SEARCH_PROGRESS", inner.getComponentCount() ) ); //NOI18N
                }
            } );
        }
    }

    @Override
    public void insertUpdate ( DocumentEvent e ) {
        for (Component c : inner.getComponents()) {
            if (c instanceof OneLibraryPanel) {
                if (!((OneLibraryPanel) c).isSelected()) {
                    inner.remove( c );
                }
            }
            if (c instanceof JLabel) {
                inner.remove( c );
            }
        }
        inner.invalidate();
        inner.revalidate();
        inner.repaint();
        statusLabel.setText( NbBundle.getMessage( LibrariesPanel.class, "SEARCHING" ) );
        progress.setIndeterminate( true );
        progress.setVisible( true );
        task.schedule( 750 );
    }

    @Override
    public void removeUpdate ( DocumentEvent e ) {
        insertUpdate( e );
    }

    @Override
    public void changedUpdate ( DocumentEvent e ) {
        insertUpdate( e );
    }

    public List<String> getLibraries () {
        List<String> result = new ArrayList<String>();
        for (Component c : inner.getComponents()) {
            if (c instanceof OneLibraryPanel) {
                if (((OneLibraryPanel) c).isSelected()) {
                    result.add( ((OneLibraryPanel) c).getModuleName() );
                }
            }
        }
        return result;
    }
}
