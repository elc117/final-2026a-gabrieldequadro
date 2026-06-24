# Gabriel de Quadro Schutz da Silva

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

## Evolução das tela

# Tela da equipe

<img width="1282" height="724" alt="image" src="https://github.com/user-attachments/assets/e4f9f6ee-a73b-49de-8470-b84beb3d10a8" />

Primeira tela do jogo, onde os comandos ainda eram feitos por teclado.

# Tela do mini-game de coletas de moeda

<img width="1282" height="724" alt="image" src="https://github.com/user-attachments/assets/a1c51958-e138-4158-953e-720da85caac4" />

Aqui foram feitos alguns testes para ver se todas as funções se adequaram corretamente ao jogo, onde fui melhorando pouco a pouco

# Tela equipe 

<img width="1858" height="1003" alt="image" src="https://github.com/user-attachments/assets/9e28ff0f-5ed8-41db-b8e8-8609b998a146" />

Tela final, onde os botões já estão clicaveis.

# tela do mini-game

<img width="1858" height="1003" alt="image" src="https://github.com/user-attachments/assets/314d5b5f-6967-42f4-9923-27dc7f216d5e" />

Tela final, acabei esquecendo de tirar print das outras versões onde errei os sprites, coloquei os objetos ainda como quadrados amarelos ou azuis.

# Diagrama de classes

<img width="663" height="582" alt="image" src="https://github.com/user-attachments/assets/7c35b3df-f096-48c0-be78-5c466993f2b3" />

Ferramenta usada para gerar o diagrama de classe: https://plantuml.com/

## Como executar

### Requisitos
- Java 17 ou superior

### Executando o projeto

Linux/macOS:

```bash
./gradlew clean
./gradlew :lwjgl3:run
```

# Fontes

Documentação oficial do LibGDX.
Documentação oficial do Java.
Tutoriais e exemplos disponibilizados pela comunidade LibGDX.
Assets:https://bakudas.itch.io/generic-dungeon-pack
Sprites de personagens e porta de saida geradas pelo chatGPT

# Prompts usados

File not found: Assets.tsx (Internal)(Assets estavam na mesma pasta do mapa.txm porém não eram reconhecidos.
foi resolvido criando outro mapa e carregando novamente os Assets.png)


enviou os sprites do guerreiro, ladino, mago, porta e guarda] e perguntou: "tenho esses sprites, como faço para anexalos ao jogo.
(Foram dadas duas opções, colocar a cordenada de onde eles apareceriam, colocando diretamente no codigo ou adicionando colisões no programa Tiled, fiz do segundo jeito e para os guerreiros, ladino e mago, acrescentei no codigo usando texture e texture region para fazer o recorte)

# VIDEO

<img width="480" height="270" alt="game" src="https://github.com/user-attachments/assets/b4c8a732-dd58-498b-8a92-4bb75a263753" />


# Tela jogo atual
https://quadrosyt.itch.io/guild-controler
