Poem Generator
==============

A poem generator using two Markov chains:
* One chain is used to build the endings of each verse - Ending Chain
* The other chain is used to build the verses (in reverse) - Word Chain

This allows the program to keep some context between different verses and
even rhyme (if the author rhymes as well)

Examples
--------

The next 4 examples have the following properties:
* Ex.1 and 2 were generated using a dataset of Shakespeare's works (English, with rhymes)
* Ex.3 and 4 were generated using a dataset of Caeiro's works (Portuguese, free verse)

All examples were generated with a word chain of degree 2 and an ending chain of degree 1.
Unfortunately, due to the small size of verses/strophes, using larger degrees quickly leads
to overfitting.

Example 1 (Shakespeare):

    The little Love-god lying once asleep,
    Laid by his side his heart-inflaming brand,
    And almost thence my nature is subdu'd
    My mistress' eyes are nothing like the dyer's hand:
    This wish I were renew'd;
    
Example 2 (Shakespeare):
    
    When my love excuse the slow offence
    To thee I speed:
    Being your slave what should I haste me thence?
    In our two loves there is no need.
    
Example 3 (Alberto Caeiro):
    
    Para ver as árvores altas.
    Agora que sinto na ausência dela.
    O que me abandona.
    E que anda com a cara dela no meio.
    
Example 4 (Alberto Caeiro):

    Com o primeiro fato merece ao menos a precedência e o mal que me acontece
    Nos dias certos; nos dias exteriores da minha vida?
    O Inverno irregular, cujas leis de aparecimento desconheço,
    Mas que existe em virtude da mesma fatalidade sublime,
    Da mesma inevitável exterioridade a mim,
    Que o calor da terra no alto do Verão
    E o frio da terra no cimo do Inverno.
