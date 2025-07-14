import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.*;

import lp2g35.biblioteca.*;

public class TabelaBusca extends JFrame {

    private JTable tabela;
    private JTextField campoBusca;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private boolean exibirUsuarios;
    private Biblioteca biblioteca;

    public TabelaBusca(Biblioteca b, boolean usuarios) {
        this.biblioteca = b;
        this.exibirUsuarios = usuarios;

        setTitle(usuarios ? "Usuários Cadastrados" : "Livros Cadastrados");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        campoBusca = new JTextField();
        campoBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarTabela(campoBusca.getText());
            }
        });
        add(campoBusca, BorderLayout.NORTH);

        modelo = new DefaultTableModel();
        tabela = new JTable(modelo);

        // Cria sorter e associa à tabela
        sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        if (usuarios) {
            modelo.addColumn("CPF");
            modelo.addColumn("Nome");
            modelo.addColumn("Idade");
            modelo.addColumn("Livros Alugados");
            modelo.addColumn("Multa Total (R$)");

            for (Usuario u : b.getUsuarios().values()) {
                String nomeCompleto = u.getNome() + " " + u.getSobrenome();
                int idade = u.idade();
                int alugados = u.possui_emprestados();
                double multa = u.valorMulta();
                modelo.addRow(new Object[]{ValidaCPF.imprimeCPF(u.getCPF()), nomeCompleto, idade, alugados,String.format("R$%.2f", multa)});
            }
        } else {
            modelo.addColumn("Código");
            modelo.addColumn("Título");
            modelo.addColumn("Categoria");
            modelo.addColumn("Disponíveis");

            for (Livro l : b.getLivros().values()) {
                int disponiveis = l.getQtdTotal() - l.getEmprestados();
                modelo.addRow(new Object[]{l.getCodigo(), l.getTitulo(), l.getCategoria(), disponiveis});
            }
        }

        setVisible(true);
    }

    private void filtrarTabela(String texto) {
        texto = texto.toLowerCase();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
}
