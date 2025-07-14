import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

import lp2g35.biblioteca.*;

public class Carregando extends JFrame {

    Color cinzaClaro = new Color(220, 220, 220);
    Color azulPastel = new Color(173, 216, 230);
    Color brancoGelo = new Color(245, 245, 245);
    Color begeClaro = new Color(255, 239, 213);
    Color verdePastel = new Color(144, 238, 144);
    Color vermelhoPastel = new Color(255, 182, 193);

    private File arquivoUsuarios = null;
    private File arquivoLivros = null;

    private JCheckBox checkUsuarios;
    private JCheckBox checkLivros;

    public Carregando() {
        this.setLayout(null);
        this.setTitle("Carregando");
        this.setSize(500, 500);
        this.setLocation(500, 100);
        this.getContentPane().setBackground(brancoGelo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane paines = new JLayeredPane();
        paines.setBounds(0, 0, 500, 500);

        // Título
        JPanel titulo = new JPanel();
        titulo.setBounds(50, 20, 400, 80);
        titulo.setBackground(azulPastel);
        titulo.setLayout(null);

        JLabel lblTitulo = new JLabel("• Carregando •", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Bradley Hand", Font.PLAIN, 38));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBounds(0, 0, 400, 80);
        titulo.add(lblTitulo);

        // Painel Usuários
        JPanel painelUsuarios = new JPanel();
        painelUsuarios.setBounds(20, 120, 460, 100);
        painelUsuarios.setBackground(begeClaro);
        painelUsuarios.setLayout(null);

        JLabel lblUsuarios = new JLabel("Arquivo Usuários:");
        lblUsuarios.setFont(new Font("Bradley Hand", Font.PLAIN, 24));
        lblUsuarios.setBounds(20, 10, 250, 30);
        painelUsuarios.add(lblUsuarios);

        JButton botaoUsuarios = new JButton("Escolher Arquivo");
        botaoUsuarios.setBounds(20, 50, 200, 30);
        botaoUsuarios.setBackground(cinzaClaro);
        botaoUsuarios.setFocusPainted(false);
        botaoUsuarios.setOpaque(true);
        botaoUsuarios.setBorderPainted(false);
        botaoUsuarios.addActionListener(e -> escolherArquivo(true));
        painelUsuarios.add(botaoUsuarios);

        checkUsuarios = new JCheckBox("Arquivo Selecionado");
        checkUsuarios.setBounds(240, 50, 200, 30);
        checkUsuarios.setBackground(begeClaro);
        checkUsuarios.setEnabled(false);
        painelUsuarios.add(checkUsuarios);

        // Painel Livros
        JPanel painelLivros = new JPanel();
        painelLivros.setBounds(20, 240, 460, 100);
        painelLivros.setBackground(begeClaro);
        painelLivros.setLayout(null);

        JLabel lblLivros = new JLabel("Arquivo Livros:");
        lblLivros.setFont(new Font("Bradley Hand", Font.PLAIN, 24));
        lblLivros.setBounds(20, 10, 250, 30);
        painelLivros.add(lblLivros);

        JButton botaoLivros = new JButton("Escolher Arquivo");
        botaoLivros.setBounds(20, 50, 200, 30);
        botaoLivros.setBackground(cinzaClaro);
        botaoLivros.setFocusPainted(false);
        botaoLivros.setOpaque(true);
        botaoLivros.setBorderPainted(false);
        botaoLivros.addActionListener(e -> escolherArquivo(false));
        painelLivros.add(botaoLivros);

        checkLivros = new JCheckBox("Arquivo Selecionado");
        checkLivros.setBounds(240, 50, 200, 30);
        checkLivros.setBackground(begeClaro);
        checkLivros.setEnabled(false);
        painelLivros.add(checkLivros);

        // Botão Confirmar
        JButton botaoConfirmar = new JButton("Confirmar");
        botaoConfirmar.setBounds(60, 370, 160, 40);
        botaoConfirmar.setBackground(verdePastel);
        botaoConfirmar.setFont(new Font("Bradley Hand", Font.PLAIN, 22));
        botaoConfirmar.setFocusPainted(false);
        botaoConfirmar.setOpaque(true);
        botaoConfirmar.setBorderPainted(false);
        botaoConfirmar.addActionListener(e -> confirmar());

        // Botão Cancelar
        JButton botaoCancelar = new JButton("Cancelar");
        botaoCancelar.setBounds(260, 370, 160, 40);
        botaoCancelar.setBackground(vermelhoPastel);
        botaoCancelar.setFont(new Font("Bradley Hand", Font.PLAIN, 22));
        botaoCancelar.setFocusPainted(false);
        botaoCancelar.setOpaque(true);
        botaoCancelar.setBorderPainted(false);
        botaoCancelar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Iniciando biblioteca sem banco de dados...");
            this.dispose();
            new Principal(new Biblioteca());
        });

        // Créditos
        JPanel creditos = new JPanel();
        creditos.setBounds(120, 430, 260, 27);
        creditos.setBackground(azulPastel);
        JLabel cr = new JLabel("Yuri Baena 2025.1", SwingConstants.CENTER);
        cr.setForeground(Color.BLACK);
        creditos.add(cr);

        // Adicionar tudo
        paines.add(titulo, 0);
        paines.add(painelUsuarios, 1);
        paines.add(painelLivros, 2);
        paines.add(botaoConfirmar, 3);
        paines.add(botaoCancelar, 4);
        paines.add(creditos, 5);

        this.add(paines);
        this.setVisible(true);
    }

    private void escolherArquivo(boolean isUsuario) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos .dat", "dat"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            if (!arquivo.getName().toLowerCase().endsWith(".dat")) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um arquivo .dat");
                return;
            }
            if (isUsuario) {
                arquivoUsuarios = arquivo;
                checkUsuarios.setSelected(true);
            } else {
                arquivoLivros = arquivo;
                checkLivros.setSelected(true);
            }
        }
    }
    private void confirmar() {
        Biblioteca biblioteca = new Biblioteca();

        if(arquivoUsuarios != null && arquivoLivros != null){
            int resultado1 = biblioteca.leArquivo(arquivoUsuarios.getAbsolutePath());
            if (resultado1 == 1) {
                int resultado2 = biblioteca.leArquivo(arquivoLivros.getAbsolutePath());
                if (resultado2 == 2) {
                    JOptionPane.showMessageDialog(this, "Usuários e Livros carregados com sucesso!");
                } else {
                    mostrarErro("Livros", resultado2);
                    return;
                }
            } else {
                mostrarErro("Usuários", resultado1);
                return;
            }
        }

        // Carregar usuários
        else if (arquivoUsuarios != null) {
            int resultado = biblioteca.leArquivo(arquivoUsuarios.getAbsolutePath());
            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "Usuários carregados com sucesso!");
            } else {
                mostrarErro("Usuários", resultado);
                return;
            }
        }

        // Carregar livros
        else if (arquivoLivros != null) {
            int resultado = biblioteca.leArquivo(arquivoLivros.getAbsolutePath());
            if (resultado == 2) {
                JOptionPane.showMessageDialog(this, "Livros carregados com sucesso!");
            } else {
                mostrarErro("Livros", resultado);
                return;
            }
        }

        // Nenhum arquivo selecionado
        else if (arquivoUsuarios == null && arquivoLivros == null) {
            JOptionPane.showMessageDialog(this, "Nenhum arquivo selecionado. Iniciando sem banco de dados...");
        }

        this.dispose();
        new Principal(biblioteca);
    }


    private void mostrarErro(String tipo, int codigo) {
        String mensagem = switch (codigo) {
            case 3 -> "Tipo de conteúdo inválido no arquivo de " + tipo + ".";
            case 4 -> "Arquivo de " + tipo + " com formato inválido.";
            case 5 -> "Erro de leitura: não foi possível abrir o arquivo de " + tipo + ".";
            case 6 -> "Classe não encontrada ao ler o arquivo de " + tipo + ".";
            default -> "Erro desconhecido ao carregar o arquivo de " + tipo + ".";
        };
        JOptionPane.showMessageDialog(this, mensagem, "Erro ao carregar " + tipo, JOptionPane.ERROR_MESSAGE);
    }
}
