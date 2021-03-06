package org.uml.newcode;

import java.awt.Frame;
import java.io.File;
import java.util.ArrayList;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.project.Project;
import org.uml.filetype.cdg.renaming.MyClassDiagramRenameTable;
import org.uml.model.ClassDiagram;

/**
 * Dialog which allows for selection of the Java project in which the code
 * should be generated.
 *
 * @author Uros
 */
public class GenerateCodeDialog extends javax.swing.JDialog implements AutoCloseable {

    /**
     * Class diagram for which we should generate code.
     */
    private final ClassDiagram classDiagram;
    private final MyClassDiagramRenameTable renames;

    /**
     * List of all open Java projects.
     */
    private final ArrayList<Project> projectsList = new ArrayList<>();

    /**
     * Constructor specifying the class diagram to generate the code from.
     * Initalizes the GUI.
     *
     * @param classDiagram
     * @param renames
     * @throws java.lang.Exception
     */
    public GenerateCodeDialog(ClassDiagram classDiagram, MyClassDiagramRenameTable renames) throws Exception {
        super((Frame) null, true);
        this.classDiagram = classDiagram;
        this.renames = renames;
        initComponents();
        buildComboBoxModel();
        if (projectsList.isEmpty()) {
            throw new Exception("Java code must be generated to a Java project. Please open a Java project.");
        }
    }

    /**
     * Fills the projects combo box wich all open Java projects.
     */
    public final void buildComboBoxModel() {
        cbxProjects.removeAllItems();
        Project[] projects = OpenProjects.getDefault().getOpenProjects();

        for (Project project : projects) {
            String projectClass = project.getClass().getSimpleName();
            if (projectClass.equals("J2SEProject") || projectClass.equals("NbMavenProjectImpl")) {
                cbxProjects.addItem(project.getProjectDirectory().getName());
                projectsList.add(project);
            }
        }
    }

    @Override
    public void close() throws Exception {
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbxProjects = new javax.swing.JComboBox<String>();
        lblProject = new javax.swing.JLabel();
        btnGenerateCode = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(GenerateCodeDialog.class, "GenerateCodeDialog.title")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lblProject, org.openide.util.NbBundle.getMessage(GenerateCodeDialog.class, "GenerateCodeDialog.lblProject.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(btnGenerateCode, org.openide.util.NbBundle.getMessage(GenerateCodeDialog.class, "GenerateCodeDialog.btnGenerateCode.text")); // NOI18N
        btnGenerateCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateCodeActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(btnCancel, org.openide.util.NbBundle.getMessage(GenerateCodeDialog.class, "GenerateCodeDialog.btnCancel.text")); // NOI18N
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblProject)
                        .addGap(18, 18, 18)
                        .addComponent(cbxProjects, 0, 300, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGenerateCode)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblProject)
                    .addComponent(cbxProjects, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerateCode)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Generates code into the selected Java project's path.
     *
     * @param evt
     */
    private void btnGenerateCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateCodeActionPerformed
        Project project = projectsList.get(cbxProjects.getSelectedIndex());
        String projectPath = project.getProjectDirectory().getPath() + File.separator;
        String projectSourcePath = projectPath + "src" + File.separator;
        String projectClass = project.getClass().getSimpleName();
        if (projectClass.equals("NbMavenProjectImpl")) {
            projectSourcePath += "main" + File.separator + "java" + File.separator;
        }
        ClassDiagramCodeGenerator.generateOrUpdateCode(classDiagram, renames, projectSourcePath);
        dispose();
    }//GEN-LAST:event_btnGenerateCodeActionPerformed

    /**
     * Closes the dialog.
     *
     * @param evt
     */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnGenerateCode;
    private javax.swing.JComboBox<String> cbxProjects;
    private javax.swing.JLabel lblProject;
    // End of variables declaration//GEN-END:variables

}
