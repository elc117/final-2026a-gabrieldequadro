#Gabriel de Quadro Schutz da Silva

Gostaria de fazer um slide scroller, onde durante a parte do dia será administrado um tipo de guilda, onde terá uma pequena parte de gerenciamento ( venda de itens, recrutamento para o grupo).

# Observação sobre o desenvolvimento do projeto

Como comentei até o dia 11 fique sem computador e acabei apenas focando em fazer a logica do jogo onde comecei a desenvolver inicialmente todas as regras de como meu jogo iria funcionar.
Enquanto fazia essa primeira parte do jogo utilizei alguns recurso de orientação a objetos.

Começando pela Classe abstrata MembroGuilda, que representa os personagens recrutaveis e a partir dela criei Guerreiro, Mago e Ladino onde foi utilizado herança para compartilhar atributos em comum.

Após usei polimorfismo para o metodo exibirHabilidade, onde cada classe filha implementa de maneira diferente, permitindo que cada personagem tenha uma habilidade propria sem que o sistema precise conhecer o detalhe de cada uma dela.
(durante a implementação, como poderá ser visto nos commit's fiz testes para cada uma delas separadamente após criar porém não foi totalmente implementada como elementos jogaveis dentro do jogo.)

encapsulamento foi usado para que eu pudesse privar nome, nivel, custo.


No dia 12, 13 e 14 passei para a parte das telas, onde crie em principio duas telas que em primeiro momento tem como foco o teste da logica para ver se tudo esta funcionando como deveria, desde os cliques em teclas para aquisição de membros para o grupo, equipar itens, usar habilidades e percorer obstaculos de acordo com oque propus a fazer na logica, no começo tive um pouco de dificuldade pois não conhecia bem as ferramentas usadas, após algumas olhadas vi que para interagir com um objeto solido precisava fazer uma "hitbox" e tive novas ideias para, como adicionar um "Guarda" que não pode ver você se não a missão falha.

Retornando no dia 21 comecei a fazer algumas partes de melhora visual, onde troquei a interface. Antes estava sendo feita por comandos via teclado que foi substituida por uma interface clicavel, a principal diferença foi a apresentação, pois antes era feito através de cordenadas onde após a mudança foram utilizadas tavelas da biblioteca Scene2D que permite um posicionamento automatico que facilitou muito a criação e adequação da mesma.

No dia 22 comecei a fazer o mapa usando a ferramenta Tiled que pode ser encontrada no proprio itch.io, no começo achei simples porém ao começar os primeiros testes me deparei com alguns desafios, alguns deles foram o tamanho do pulo, onde tive que calcular a força do pulo que eu setei anteriormente junto da gravidade, após isso cheguei ao 126 pixel que equivalem a +- 4 blocos levando em conta que meus tileset são 16x16.

No dia 23 apenas adicionei alguns sprites, terminei o mapa coloquei uma imagem de fundo e arrumei alguns dos tiles que haviam ficado travando o personagem por serem mais altos que outros, adicionei o guarda ao mapa e deixei com que fosse possivel passar por ele porém ainda com a função de caso entre no campo de visão do mesmo você perca.



https://quadrosyt.itch.io/guild-controler
