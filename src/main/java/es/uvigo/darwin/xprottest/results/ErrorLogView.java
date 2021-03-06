/*
Copyright (C) 2009  Diego Darriba

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package es.uvigo.darwin.xprottest.results;

import es.uvigo.darwin.prottest.util.logging.ProtTestLogFormatter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import es.uvigo.darwin.xprottest.util.TextAreaAppender;

import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 *
 * @author  Diego Darriba
 */
public class ErrorLogView extends javax.swing.JFrame {

	private static final long serialVersionUID = -2022588731545645833L;
	
	private PrintWriter errorWriter;
    private Handler logHandler;
    
    public Handler getLogHandler() {
        return logHandler;
    }
    
    /** Creates new form ErrorLogView */
    public ErrorLogView() {
        initComponents();
        errorWriter = new PrintWriter(new TextAreaAppender(errorTextArea));
        logHandler = new StreamHandler(new OutputStream()
		  {
			protected Writer _writer = errorWriter;
		    protected String _encoding;
		    private byte[] _buf=new byte[1];
		    
			@Override
		    public void close()
		        throws IOException
		    {
		        _writer.close();
		        _writer=null;
		        _encoding=null;
		    }
		    
		    @Override
		    public void flush()
		        throws IOException
		    {
		        _writer.flush();
		    }
		    
		    @Override
		    public void write(byte[] b) 
		        throws IOException
		    {
		        if (_encoding==null)
		            _writer.write(new String(b));
		        else
		            _writer.write(new String(b,_encoding));
		    }
		    
		    @Override
		    public void write(byte[] b, int off, int len)
		        throws IOException
		    {
		        if (_encoding==null)
		            _writer.write(new String(b,off,len));
		        else
		            _writer.write(new String(b,off,len,_encoding));
		    }
		    
		    public synchronized void write(int b)
		        throws IOException
		    {
		        _buf[0]=(byte)b;
		        write(_buf);
		    }
		},
                new ProtTestLogFormatter());
        logHandler.setFilter(new Filter(){

            public boolean isLoggable(LogRecord lr) {
                return lr.getLevel().equals(Level.SEVERE);
            }
            
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        errorScrollPane = new javax.swing.JScrollPane();
        errorTextArea = new javax.swing.JTextArea();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(es.uvigo.darwin.xprottest.XProtTestApp.class).getContext().getResourceMap(ErrorLogView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        errorScrollPane.setName("errorScrollPane"); // NOI18N

        errorTextArea.setColumns(15);
        errorTextArea.setEditable(false);
        errorTextArea.setFont(resourceMap.getFont("errorTextArea.font")); // NOI18N
        errorTextArea.setRows(30);
        errorTextArea.setName("errorTextArea"); // NOI18N
        errorScrollPane.setViewportView(errorTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(errorScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane errorScrollPane;
    private javax.swing.JTextArea errorTextArea;
    // End of variables declaration//GEN-END:variables
    
}
