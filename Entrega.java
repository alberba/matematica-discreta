import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * Cada tema té el mateix pes, i l'avaluació consistirà en:
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estàndar que heu de seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No és
 *   necessari seguir-la estrictament, però ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Santiago Rattenbach Paliza-Bartolomé
 * - Nom 2: Albert Salom Vanrell
 * - Nom 3:
 * Jorge
 * L'entrega es farà a través d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més fàcilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */

class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, té com resultat un booleà. Els predicats de dues
   * variables són de tipus `BiPredicate<Integer, Integer>` i similarment s'avaluen com
   * `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per utilitzar la força bruta)
   */
  static class Tema1 {
    /*
     * És cert que ∀x,y. P(x,y) -> Q(x) ^ R(y) ?
     */
    static boolean exercici1(
        int[] universe,
        BiPredicate<Integer, Integer> p,
        Predicate<Integer> q,
        Predicate<Integer> r) {
        //Recorremos los elementos del universo
        for(int x:universe) {
            for (int y:universe) {
                // !(P(x,y) -> Q(x) ^ R(y)) <==> P(x,y) ^ !(Q(x) ^ R(y))                
                if(p.test(x,y)) {
                    // si el predicado anterior se cumple, se comprobarán
                    // los predicados siguientes
                    if(!(q.test(x)&&r.test(y))) {
                        // la condición no se cumple, devolvemos falso
                        return false;
                    }  
                }  
            }
        }
      // si llega a este punto, indica que siempre se cumple
      return true;
    }

    /*
     * És cert que ∃!x. ∀y. Q(y) -> P(x) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
        int condicionesNoCumplidas,xcorrectos=0;
        for(int x:universe) {
            condicionesNoCumplidas=0;
            for (int y:universe) {
                //Q(y) -> P(x) <==> !(Q(y) V !P(x))
                // buscamos el caso en que no se cumpla
                if(q.test(y)) {
                    if(!p.test(x)) {
                        //Q(y) -> P
                        condicionesNoCumplidas++;
                    }  
                }  
            }
            // se comprueba si la condición se cumple para todo y
            if(condicionesNoCumplidas==0) {
                xcorrectos++;           
            }
        }
        // si el resultado es diferente a 1, indica que o no hay ninguna x 
        // que cumpla la condición o que no hay solo una x que la cumple,
        // por tanto, no se cumple el enunciado
        return xcorrectos==1;
    }

    /*
     * És cert que ¬(∃x. ∀y. y ⊆ x) ?
     *
     * Observau que els membres de l'univers són arrays, tractau-los com conjunts i podeu suposar
     * que cada un d'ells està ordenat de menor a major.
     */
    static boolean exercici3(int[][] universe) {
        boolean existeX = false;
        // se recorre el universo
        for (int[] y : universe) {
            for (int[] x : universe) {
              // si x<y, no se puede dar el predicado
                if (x.length < y.length) {
                    existeX = false;
                } else {
                    int pertenecenA_X = 0;
                    // se recorren los elementos de y para ver si pertenecen a X
                    for (int yn:y) {
                        boolean tieneX=false;
                        // se buscan elementos coincidentes entre los conjuntos x e y
                        for (int xn:x) {
                            // si se encuentra una x que coincida, dejamos de buscar
                            // para este elemento de y
                            if (!tieneX && (yn == xn)) {
                                pertenecenA_X++;
                                tieneX=true;
                            }
                        }
                        // si no encontramos ya un y que sea subconjunto de x
                        // verificamos si lo es el conjunto de esta iteración
                        if (!existeX) {
                            existeX = (pertenecenA_X == y.length);
                        }
                    }
                }
            }
            // si algún y no es subconjunto de ningún x, devolvemos true
            if (!existeX) {
                return true;
            }
        }
        // llegados a este punto, alguna x tendrá como subconjunto
        // todos los conjuntos y
        return false;
    }

    /*
     * És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?
     */
    static boolean exercici4(int[] universe, int n) {
        // se recorre el universo
        for (int x:universe) {
            boolean tieneUnaY=false;
            for (int y:universe) {
                // se verifica que y sea inverso de x
                if ((x*y)%n==1) {
                    // si ya se ha encontrado algún inverso
                    // y se encuentra otro, entonces el enunciado
                    // no se cumple
                    if (tieneUnaY) {
                        return false;
                    }
                    // esta x ahora tiene un inverso,
                    // con este boolean verificaremosmos que
                    // siempre sea así
                    tieneUnaY=true;        
                }
            }
            // si no se encuentra ningún inverso de x,
            // se devuelve falso
            if (!tieneUnaY) {
                return false;
            }  
        }
        // legados a este punto, todas las x tendrán exactamente
        // una y tal que x·y ≡ 1 (mod n)
        return true;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x,y. P(x,y) -> Q(x) ^ R(y)

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              (x, y) -> x * y <= 4,
              x -> x <= 3,
              x -> x <= 3
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              (x, y) -> x * y >= 0,
              x -> x >= 0,
              x -> x >= 0
          )
      );

      // Exercici 2
      // ∃!x. ∀y. Q(y) -> P(x) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              x -> x < 0,
              x -> true
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0  // x és múltiple de 4
          )
      );

      // Exercici 3
      // ¬(∃x. ∀y. y ⊆ x) ?

      assertThat(
          exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {} })
      );

      assertThat(
          !exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3} })
      );

      // Exercici 4
      // És cert que ∀x. ∃!y. x·y ≡ 1 (mod n) ?

      assertThat(
          exercici4(
              new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
              11
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 5, 7 },
              13
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com arrays (sense
   * elements repetits). Per tant, un conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant int[] a,
   * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar com f.apply(x) (on
   * x és un enter d'a i el resultat f.apply(x) és un enter de b).
   */
  static class Tema2 {

    /*
     * És `p` una partició d'`a`?
     *
     * `p` és un array de conjunts, haureu de comprovar que siguin elements d'`a`. Podeu suposar que
     * tant `a` com cada un dels elements de `p` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] p) {
        boolean [] enUso=new boolean[a.length];
        // se recorren los elementos de p
        for (int [] pn:p) {
            // se reinicia el array que verifica si los elementos de la partición están en uso
            boolean [] enUsoParticion=new boolean[pn.length];
            // se recorren los elementos de a
            for (int an=0;an<a.length;an++) {
                // y los elementos de la posible partición 
                for (int pni=0;pni<pn.length;pni++) {
                    // se buscan coincidencias (con valores que no tengan ya coincidencias)
                    if (pn[pni]==a[an]&&!enUso[an]) {
                        // al encontrar una coincidencia, señalamos que ambos valores
                        // tienen una coincidencia
                        enUso[an]=true;
                        enUsoParticion[pni]=true;
                    }
                } 
            }
            // se recorren el array de booleans que verifica si cada elemento tiene coincidencia
            for(int pni=0;pni<pn.length;pni++){
                // si un elemento no tiene coincidencia, entonces este elemento de p
                // no es una partición de a, por ende, no se cumple el enunciado
                if(!enUsoParticion[pni]){
                    return false;
                }
            }
        }
        // llegados a este punto, podemos decir que todos los elementos de p son particiones de a
        return true;
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és un ordre parcial i que `x` n'és el mínim.
     *
     * Podeu soposar que `x` pertany a `a` i que `a` està ordenat de menor a major.
     */
    static boolean exercici2(int[] a, int[][] rel, int x) {
        // orden parcial -> reflexiva, antisimétrica y transitiva
        boolean esTrans;
        boolean [] cumpleCondicion;
        int relacionesX=0;
      
        // se comprueba que x sea el mínimo de a
        for (int [] reln:rel) {
            if (x==reln[0]) {
                relacionesX++;
            }
        }
        // si x se relaciona con todos los elementos de a, entonces
        // es mínimo, en caso contrario, no lo será
        if (relacionesX!=a.length) {
            return false;
        }
        
        // reflexiva
        cumpleCondicion=new boolean[a.length];
        for (int an=0;an<a.length;an++) {    
            for (int [] reln:rel) {
                // se comprueba que los elementos de la relación
                // sean el mismo
                if (a[an]==reln[0]&&a[an]==reln[1]) {
                    cumpleCondicion[an]=true;
                }
            }
        }
        // si algún elemento de a no se relaciona con él mismo,
        // entonces podemos decir que la relación no es reflexiva
        for (int an=0;an<a.length;an++) { 
            if (!cumpleCondicion[an]) {
                return false;
            }
        }
        
        // antisimétrica
        // para cada relación, buscamos otra con los mismos
        // elementos, pero invertidos
        for(int an=0;an<a.length;an++){    
            for (int [] reln1:rel) {
                int x1=reln1[0],y1=reln1[1];
                for (int [] reln2:rel) {
                    int x2=reln2[0],y2=reln2[1];
                    if (x1==y2&&x2==y1) {
                        // si al encontrar la relación con elementos invertidos,
                        // vemos que los elementos son distintos, no es antisimétrica
                        if (!(x1==y1)) {
                            return false;
                        }
                    }
                }
            }
        }
        
        // transitiva
        // se buscan dos relaciones, en las que el segundo componente
        // de la primera relación coincida con el primer componente de
        // la segunda relación
        for (int an=0;an<a.length;an++) {    
            for (int [] reln1:rel) {
                int x1=reln1[0],y1=reln1[1];
                for (int [] reln2:rel) {
                    int x2=reln2[0],y2=reln2[1];
                    // se encuentran a R b y b R c
                    if (y1==x2&&x1!=y2) {
                        esTrans=false;
                        for (int [] reln3:rel) {
                            int x3=reln3[0],y3=reln3[1];
                            // se comprueba a R c
                            if (x3==x1&&y3==y2){
                                esTrans=true;
                            }
                        }
                        // si no se encuentra a R c, devuelve falso
                        if (!esTrans) {
                            return false;
                        }
                    }
                }
            }    
        }
        // legados a este punto, podemos decir que la relación es de orden parcial
        return true;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Trobau l'antiimatge de
     * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`). Podeu suposar
     * que `y` pertany a `codom` i que tant `dom` com `codom` també estàn ordenats de menor a major.
     */
    static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
        int indiceCumplen=0;
        int [] numsSeCumplen=new int[dom.length];
        for (int x:dom) {
            if (f.apply(x)==y) {
                numsSeCumplen[indiceCumplen]=x;
                indiceCumplen++;
                
            }
        }
        int [] antiImagen=new int[indiceCumplen];
        for (int indice=0;indice<indiceCumplen;indice++) {
            antiImagen[indice]=numsSeCumplen[indice];
        }
        Arrays.sort(antiImagen);
        
        return antiImagen; // TO DO
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - 3 si `f` és bijectiva
     * - 2 si `f` només és exhaustiva
     * - 1 si `f` només és injectiva
     * - 0 en qualsevol altre cas
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major. Per comoditat, podeu
     * utilitzar les constants definides a continuació:
     */
    static final int NOTHING_SPECIAL = 0;
    static final int INJECTIVE = 1;
    static final int SURJECTIVE = 2;
    static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
        int [] numAntiImagen=new int[codom.length];
        boolean esInyectiva=true,esExhaustiva=true;
        for (int y=0;y<codom.length;y++) {
            for (int x:dom) {
                if (f.apply(x)==codom[y]) {
                    numAntiImagen[y]++;
                }
                if (numAntiImagen[y]>1) {
                    esInyectiva=false;
                }
            }
            if (numAntiImagen[y]==0) {
                esExhaustiva=false;
            }
        }
        if(esInyectiva&&!esExhaustiva) {
            return INJECTIVE;
        } else if(!esInyectiva&&esExhaustiva) {
            return SURJECTIVE;
        } else if(esInyectiva&&esExhaustiva) {
            return BIJECTIVE;
        } else {
            return NOTHING_SPECIAL;
        }
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `p` és una partició d'`a`?

      assertThat(
          exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {3, 5}, {4} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 1, 2, 3, 4, 5 },
              new int[][] { {1, 2}, {5}, {1, 4} }
          )
      );

      // Exercici 2
      // és `rel` definida sobre `a` d'ordre parcial i `x` n'és el mínim?

      ArrayList<int[]> divisibility = new ArrayList<int[]>();

      for (int i = 1; i < 8; i++) {
        for (int j = 1; j <= i; j++) {
          if (i % j == 0) {
            // i és múltiple de j, és a dir, j|i
            divisibility.add(new int[] { j, i });
          }
        }
      }

      assertThat(
          exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2}, {3, 3}, {1, 2}, {2, 3} },
              1
          )
      );

      assertThat(
          !exercici2(
              new int[] { 1, 2, 3, 4, 5, 6, 7 },
              divisibility.toArray(new int[][] {}),
              2
          )
      );

      // Exercici 3
      // calcular l'antiimatge de `y`

      assertThat(
          Arrays.equals(
              new int[] { 0, 2 },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1 },
                  x -> x % 2, // residu de dividir entre 2
                  0
              )
          )
      );

      assertThat(
          Arrays.equals(
              new int[] { },
              exercici3(
                  new int[] { 0, 1, 2, 3 },
                  new int[] { 0, 1, 2, 3, 4 },
                  x -> x + 1,
                  0
              )
          )
      );

      // Exercici 4
      // classificar la funció en res/injectiva/exhaustiva/bijectiva

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> (x + 1) % 4
          )
          == BIJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3, 4 },
              x -> x + 1
          )
          == INJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1 },
              x -> x / 2
          )
          == SURJECTIVE
      );

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3 },
              new int[] { 0, 1, 2, 3 },
              x -> x <= 1 ? x+1 : x-1
          )
          == NOTHING_SPECIAL
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Aritmètica).
   *
   */
  static class Tema3 {
    /*
     * Donat `a`, `b` retornau el màxim comú divisor entre `a` i `b`.
     *
     * Podeu suposar que `a` i `b` són positius.
     */
    static int exercici1(int a, int b) {
        int divisor, dividendo;
        if (a>b) {
            divisor=b;
            dividendo=a;
        }
        else {
            divisor=a;
            dividendo=b;
        }
        int resto=dividendo%divisor;
        
        while(resto!=0) {
            dividendo=divisor;
            divisor=resto;
            resto=dividendo%divisor;
        } 
        return divisor; // TO DO
    }

    /*
     * Es cert que `a``x` + `b``y` = `c` té solució?.
     *
     * Podeu suposar que `a`, `b` i `c` són positius.
     */
    static boolean exercici2(int a, int b, int c) {
        int mcd=exercici1(a,b);
        return (c%mcd==0); // TO DO
    }

    /*
     * Quin es l'invers de `a` mòdul `n`?
     *
     * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
     */
    static int exercici3(int a, int n) {
        int resultado;
        if (exercici1(a,n)!=1) {
            return -1; 
        }
        for (int x=0;x<n;x++) {
            resultado=((a*x)%n);
            if (resultado==1) {
                return x;
            }
        }
        return -1; // TO DO
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `mcd(a,b)`

      assertThat(
              exercici1(2, 4) == 2
      );

      assertThat(
              exercici1(1236, 984) == 12
      );

      // Exercici 2
      // `a``x` + `b``y` = `c` té solució?

      assertThat(
              exercici2(4,2,2)
      );
      assertThat(
              !exercici2(6,2,1)
      );
      // Exercici 3
      // invers de `a` mòdul `n`
      assertThat(exercici3(2, 5) == 3);
      assertThat(exercici3(2, 6) == -1);
    }
  }
  
  static class Tema4 {
    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, retornau l'ordre i la mida del graf.
     */
    static int[] exercici1(int[][] A) {
        int ordre=0;
        int mida=0;
        // cada fila de la matriz es un nodo
        for (int [] v:A) {
            ordre++;
            // los elementos de una fila representan las aristas
            for (int vi:v) {
                mida+=vi;
            }
        }
        // las aristas se cuentan dos veces en la matriz, por tanto
        // dividimos entre 2
        mida/=2;
      return new int[]{ordre, mida}; // TO DO
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf no dirigit, digau si el graf es eulerià.
     */
    static boolean exercici2(int[][] A) {
        // determinamos el grado de cada nodo
        for (int [] v:A) {
            int aristas=0;
            for (int vi:v) {
                aristas+=vi;
            }
            // si un nodo tiene un número de aristas impares, no es euleriano
            if (aristas%2==1) {
                return false;
            }
        }
      return true; // TO DO
    }

    /*
     * Donat `n` el número de fulles d'un arbre arrelat i `d` el nombre de fills dels nodes interiors,
     * retornau el nombre total de vèrtexos de l'arbre
     *
     */
    static int exercici3(int n, int d) {
        // utilizando la regla de las manos entrelazadas, acabamos con una equación
        // de primer grado donde el resultado es el siguiente
        return (n*d-1)/(d-1);
    }

    /*
     * Donada una matriu d'adjacencia `A` d'un graf connex no dirigit, digau si el graf conté algún cicle.
     */
    static boolean exercici4(int[][] A) {
        // obtenemos los datos (datos[0]=orden, datos[1]=tamaño) utilitzando el ejercicio 1
        int [] datos=exercici1(A);
        // el único grafo sin ciclos es un árbol, en el cual V-1=E
        return datos[0]-1 != datos[1]; 
    }
    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `ordre i mida`

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}}), new int[] {3, 2})
      );

      assertThat(
              Arrays.equals(exercici1(new int[][] { {0, 1, 0, 1}, {1, 0, 1, 1}, {0 , 1, 0, 1}, {1, 1, 1, 0}}), new int[] {4, 5})
      );

      // Exercici 2
      // `Es eulerià?`

      assertThat(
              exercici2(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici2(new int[][] { {0, 1, 0}, {1, 0, 1}, {0,1, 0}})
      );
      // Exercici 3
      // `Quants de nodes té l'arbre?`
      assertThat(exercici3(5, 2) == 9);
      assertThat(exercici3(7, 3) == 10);

      // Exercici 4
      // `Conté algún cicle?`
      assertThat(
              exercici4(new int[][] { {0, 1, 1}, {1, 0, 1}, {1, 1, 0}})
      );
      assertThat(
              !exercici4(new int[][] { {0, 1, 0}, {1, 0, 1}, {0, 1, 0}})
      );

    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
