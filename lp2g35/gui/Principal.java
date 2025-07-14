import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import lp2g35.biblioteca.*;
import lp2g35.exc.*;

public class Principal extends JFrame implements MouseListener, ActionListener {

    Color cinzaClaro = new Color(220, 220, 220);
    Color cinzaEscuro = new Color(169, 169, 169);

    private JButton botao1, botao2, botao3;
    private JComboBox<String> caixa;

    private Biblioteca biblioteca;

    private TabelaBusca tabelaUsuarios;
    private TabelaBusca tabelaLivros;
    private TabelaEmprestimos janelaEmprestimos;
    private LocDev janelaLoc;
    private LocDev janelaDev;

    public Principal(Biblioteca b) {
        biblioteca = b;

        this.setLayout(null);

        JLayeredPane paines = new JLayeredPane();
        paines.setBounds(0, 0, 500, 550);

        Color azulPastel = new Color(173, 216, 230);
        Color brancoGelo = new Color(245, 245, 245);
        Color begeClaro = new Color(255, 239, 213);

        JPanel titulo = new JPanel();
        titulo.setBounds(50, 20, 400, 100);
        titulo.setLayout(null);
        titulo.setBackground(azulPastel);

        JLabel principal = new JLabel("• "+biblioteca.getNome()+" •", SwingConstants.CENTER);
        principal.setFont(new Font("Bradley Hand", Font.PLAIN, 38));
        principal.setBounds(0, 0, 400, 100);
        principal.setForeground(Color.BLACK);
        titulo.add(principal);

        JPanel botoes = new JPanel();
        botoes.setBounds(20, 135, 460, 350);
        botoes.setBackground(begeClaro);
        botoes.setLayout(null);

        JPanel tBotoes = new JPanel();
        tBotoes.setLayout(null);
        tBotoes.setBounds(120, 5, 230, 27);
        tBotoes.setBackground(azulPastel);

        JLabel selecioneLabel = new JLabel("Opções: ");
        selecioneLabel.setFont(new Font("Bradley Hand", Font.PLAIN, 17));
        selecioneLabel.setBounds(5, 4, 60, 20);  

        String[] opcoes = {"Manutenção", "Cadastrar", "Locação/Devolução", "Relatório"};
        caixa = new JComboBox<>(opcoes);
        caixa.setBounds(85, 1, 150, 27); 
        caixa.setBackground(cinzaClaro);
        caixa.setForeground(Color.BLACK);
        caixa.addActionListener(this);

        tBotoes.add(selecioneLabel);
        tBotoes.add(caixa);

        botao1 = new JButton();
        botao2 = new JButton();
        botao3 = new JButton();

        configurarBotao(botao1, 20, 35);
        configurarBotao(botao2, 20, 135);
        configurarBotao(botao3, 20, 235);

        botoes.add(tBotoes);
        botoes.add(botao1);
        botoes.add(botao2);
        botoes.add(botao3);

        JPanel creditos = new JPanel();
        creditos.setBounds(120, 500, 260, 27);
        creditos.setBackground(azulPastel);

        JLabel cr = new JLabel("Yuri Baena 2025.1", SwingConstants.CENTER);
        cr.setForeground(Color.BLACK);
        creditos.add(cr);

        paines.add(titulo, 0);
        paines.add(botoes, 1);
        paines.add(creditos, 2);

        this.add(paines);

        this.setTitle("Principal");
        this.getContentPane().setBackground(brancoGelo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 550);
        this.setResizable(false);
        this.setLocation(500, 0);
        this.setVisible(true);

        // Inicializa botões com a primeira opção
        atualizarBotoes(opcoes[0]);
    }

    private void configurarBotao(JButton botao, int x, int y) {
        botao.setFont(new Font("Bradley Hand", Font.PLAIN, 28));
        botao.setBackground(cinzaClaro);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setBounds(x, y, 420, 90);
        botao.addMouseListener(this);
    }

    private Usuario criaUsu() {
        String nome, sobrenome, dia, mes, ano, cpf, end;

        do {
            nome = JOptionPane.showInputDialog(this, "Digite o nome:");
            if (nome == null) return null;  
        } while (!Validador.nome(nome));

        do {
            sobrenome = JOptionPane.showInputDialog(this, "Digite o sobrenome:");
            if (sobrenome == null) return null;
        } while (!Validador.sobrenome(sobrenome));

        do {
            dia = JOptionPane.showInputDialog(this, "Digite o dia de nascimento:");
            if (dia == null) return null;
        } while (!Validador.dia(dia));

        do {
            mes = JOptionPane.showInputDialog(this, "Digite o mês de nascimento:");
            if (mes == null) return null;
        } while (!Validador.mes(mes));

        do {
            ano = JOptionPane.showInputDialog(this, "Digite o ano de nascimento:");
            if (ano == null) return null;
        } while (!Validador.ano(ano));

        do {
            cpf = JOptionPane.showInputDialog(this, "Digite o CPF:");
            if (cpf == null) return null;
        } while (!Validador.cpf(cpf));

        end = JOptionPane.showInputDialog(this, "Digite o endereço:");
        if (end == null) return null;

        //verifica se Usuario ja existe
        try{
            biblioteca.getUsuario(Long.parseLong(cpf));
        }
        catch(Exception e){
            return new Usuario(nome, sobrenome, dia, mes, ano, cpf, end);
        }
        
        return null;
    }

    private Livro criaLiv() {
        Integer codigo = null;
        String titulo, categoria;
        Integer qtd_total = null;

        // Código
        do {
            String entrada = JOptionPane.showInputDialog(this, "Digite o código do livro:");
            if (entrada == null) return null;

            try {
                int cod = Integer.parseInt(entrada);
                codigo = cod;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Código inválido! Digite um número inteiro.");
            }
        } while (codigo == null);

        // Título
        do {
            titulo = JOptionPane.showInputDialog(this, "Digite o título do livro:");
            if (titulo == null) return null;
        } while (titulo.isEmpty());

        // Categoria
        do {
            categoria = JOptionPane.showInputDialog(this, "Digite a categoria do livro:");
            if (categoria == null) return null;
        } while (categoria.isEmpty());

        // Quantidade total
        do {
            String entrada = JOptionPane.showInputDialog(this, "Digite a quantidade total de cópias:");
            if (entrada == null) return null;

            try {
                int qtd = Integer.parseInt(entrada);
                if (qtd < 0) {
                    JOptionPane.showMessageDialog(this, "Quantidade deve ser maior ou igual a zero.");
                    qtd_total = null;
                } else {
                    qtd_total = qtd;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantidade inválida! Digite um número inteiro.");
            }
        } while (qtd_total == null);

        int qtd_emprestada = 0;

        //verifica se Usuario ja existe
        try{
            biblioteca.getLivro(codigo);
        }
        catch(Exception e){
            return new Livro(codigo, titulo, categoria, qtd_total, qtd_emprestada);
        }
        
        return null;
    }

    private String pedirNomeArquivo(String mensagem) {
        String nomeArquivo;
        do {
            nomeArquivo = JOptionPane.showInputDialog(this, mensagem);

            if (nomeArquivo == null) {
                return null; // Cancelado
            }

            nomeArquivo = nomeArquivo.trim();

            if (nomeArquivo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O nome do arquivo não pode ser vazio!");
            } else if (!nomeArquivo.toLowerCase().endsWith(".dat")) {
                JOptionPane.showMessageDialog(this, "O nome do arquivo deve terminar com \".dat\".");
                nomeArquivo = ""; // força a repetição
            }
        } while (nomeArquivo.isEmpty());

        return nomeArquivo;
    }

    private void salvar(int escolha, String nome){
        if(escolha == 0){
            //Salva Usuarios
            biblioteca.salvaArqUsu(nome);
            //Mostra ao usuario que salvou
            JOptionPane.showMessageDialog(this, "Usuários salvos em "+nome);
        }
        if(escolha == 1){
            //Salva Livros
            biblioteca.salvaArqLiv(nome);
            //Mostra ao usuario que salvou
            JOptionPane.showMessageDialog(this, "Livros salvos em "+nome);
        }
        if(escolha == 2){
            //Salva Usuarios
            biblioteca.salvaArqUsu("u.dat");
            //Salva Livros
            biblioteca.salvaArqLiv("l.dat");
            //Mostra ao usuario que salvou
            JOptionPane.showMessageDialog(this, "Usuários salvos em u.dat, Livros salvos em l.dat");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == caixa) {
            String selecionado = (String) caixa.getSelectedItem();
            atualizarBotoes(selecionado);
        }
    }

    private void atualizarBotoes(String opcao) {
        // Remove action listeners antigos para evitar chamadas duplicadas
        for (ActionListener al : botao1.getActionListeners()) botao1.removeActionListener(al);
        for (ActionListener al : botao2.getActionListeners()) botao2.removeActionListener(al);
        for (ActionListener al : botao3.getActionListeners()) botao3.removeActionListener(al);

        switch (opcao) {
            case "Manutenção":
                botao1.setText("Salvar Padrão");
                botao1.setToolTipText("Salva usuários e livros com nome padrão");
                botao1.addActionListener(ev -> {
                    salvar(2, "");
                });

                botao2.setText("Salvar Usuários");
                botao2.setToolTipText("Salva usuários com o nome especificado");
                botao2.addActionListener(ev -> {
                    String nomeArq = pedirNomeArquivo("Diga o nome do arquivo para salvar os usuários: ");
                    //avaliar antes de salvar
                    if(nomeArq != null){
                       salvar(0, nomeArq); 
                    }
                });

                botao3.setText("Salvar Livros");
                botao3.setToolTipText("Salva livros com o nome especificado");
                botao3.addActionListener(ev -> {
                    String nomeArq = pedirNomeArquivo("Diga o nome do arquivo para salvar os livros: ");
                    //avaliar antes de salvar
                    if(nomeArq != null){
                       salvar(1, nomeArq); 
                    }
                });
                break;

            case "Cadastrar":
                botao1.setText("Cadastrar Usuário");
                botao1.setToolTipText("Cadastrar novo usuário");
                botao1.addActionListener(ev -> {
                    Usuario novoUsuario = criaUsu();
                    if (novoUsuario != null) {
                        biblioteca.cadastraUsuario(novoUsuario);
                        JOptionPane.showMessageDialog(this, "Usuário cadastrado: " + novoUsuario.getNome());
                    }
                });

                botao2.setText("Cadastrar Livro");
                botao2.setToolTipText("Cadastrar novo livro");
                botao2.addActionListener(ev -> {
                    Livro novoLivro = criaLiv();
                    if (novoLivro != null) {
                        biblioteca.cadastraLivro(novoLivro);
                        JOptionPane.showMessageDialog(this, "Livro cadastrado: " + novoLivro.getTitulo());
                    }
                });

                botao3.setText("Salvar Rápido");
                botao3.setToolTipText("Salva objetos com nome padrão");
                botao3.addActionListener(ev -> {
                    salvar(2,"");
                });
                break;

            case "Locação/Devolução":
                botao1.setText("Locação");
                botao1.setToolTipText("Alugar um livro para usuário");
                botao1.addActionListener(ev -> {
                    if (janelaLoc == null || !janelaLoc.isDisplayable()) {
                        janelaLoc = new LocDev("Alocando", biblioteca,1);
                    } else {
                        janelaLoc.toFront();
                    }
                });

                botao2.setText("Devolução");
                botao2.setToolTipText("Devolver livro alugado");
                botao2.addActionListener(ev -> {
                    if (janelaDev == null || !janelaDev.isDisplayable()) {
                        janelaDev = new LocDev("Desalocando", biblioteca,2);
                    } else {
                        janelaDev.toFront();
                    }
                });

                botao3.setText("Exibir Livros");
                botao3.setToolTipText("Consultar status dos livros rapidamente");
                botao3.addActionListener(ev -> {
                    if (tabelaLivros == null || !tabelaLivros.isDisplayable()) {
                        tabelaLivros = new TabelaBusca(biblioteca, false);
                    } else {
                        tabelaLivros.toFront();
                    }
                });
                break;

            case "Relatório":
                botao1.setText("Exibir Usuários");
                botao1.setToolTipText("Gerar relatório de usuários");
                botao1.addActionListener(ev -> {
                    if (tabelaUsuarios == null || !tabelaUsuarios.isDisplayable()) {
                        tabelaUsuarios = new TabelaBusca(biblioteca, true);
                    } else {
                        tabelaUsuarios.toFront();
                    }
                });

                botao2.setText("Exibir Livros");
                botao2.setToolTipText("Gerar relatório de livros");
                botao2.addActionListener(ev -> {
                    if (tabelaLivros == null || !tabelaLivros.isDisplayable()) {
                        tabelaLivros = new TabelaBusca(biblioteca, false);
                    } else {
                        tabelaLivros.toFront();
                    }
                });

                botao3.setText("Pesquisar");
                botao3.setToolTipText("Gerar relatório de um livro/usuário especifico");
                botao3.addActionListener(ev -> {
                    if (janelaEmprestimos == null || !janelaEmprestimos.isDisplayable()) {
                        janelaEmprestimos = new TabelaEmprestimos(biblioteca);
                    } else {
                        janelaEmprestimos.toFront();
                    }
                });
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton botao = (JButton) e.getSource();
            botao.setBackground(cinzaEscuro);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton botao = (JButton) e.getSource();
            botao.setBackground(cinzaClaro);
        }
    }

    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }

}
