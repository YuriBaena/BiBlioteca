import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lp2g35.biblioteca.*;
import lp2g35.exc.NaoAlocouLivroEx;

public class TabelaEmprestimos extends JFrame {

    public TabelaEmprestimos(Biblioteca biblioteca) {
        setTitle("Empréstimos Ativos");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Campo de busca
        JTextField campoBusca = new JTextField();
        campoBusca.setFont(new Font("Arial", Font.PLAIN, 16));
        campoBusca.setToolTipText("Digite para buscar por nome do livro ou usuário...");
        add(campoBusca, BorderLayout.NORTH);

        // Modelo da tabela
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Livro");
        modelo.addColumn("Usuário");
        modelo.addColumn("Data de Empréstimo");

        JTable tabela = new JTable(modelo);
        tabela.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        // Filtro ao digitar
        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            private void filtrar() {
                String texto = campoBusca.getText().trim().toLowerCase();
                if (texto.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                }
            }

            @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
            @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
            @Override public void changedUpdate(DocumentEvent e) { filtrar(); }
        });

        // Preenchendo a tabela
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Livro livro : biblioteca.getLivros().values()) {
            for (Long cpf : livro.getUsuariosAtualmenteEmprestados()) {
                Usuario usuario = biblioteca.getUsuarios().get(cpf);
                if (usuario != null) {
                    try {
                        LocalDate dataEmprestimo = usuario.alocou(livro);
                        String nomeLivro = livro.getTitulo();
                        String nomeUsuario = usuario.getNome() + " " + usuario.getSobrenome();
                        String data = dataEmprestimo.format(formatter);

                        modelo.addRow(new Object[]{nomeLivro, nomeUsuario, data});
                    } catch (NaoAlocouLivroEx ex) {
                        // Segurança
                    }
                }
            }
        }

        setVisible(true);
    }
}
