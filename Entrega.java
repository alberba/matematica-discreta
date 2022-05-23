import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;

/*
 * Aquesta entrega consisteix en implementar tots els mÃ¨todes annotats amb el comentari "// TO DO".
 *
 * Cada tema tÃ© el mateix pes, i l'avaluaciÃ³ consistirÃ  en:
 *
 * - Principalment, el correcte funcionament de cada mÃ¨tode (provant amb diferents entrades). Teniu
 *   alguns exemples al mÃ¨tode `main`.
 *
 * - La neteja del codi (pensau-ho com faltes d'ortografia). L'estÃ ndar que heu de seguir Ã©s la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . No Ã©s
 *   necessari seguir-la estrictament, perÃ² ens basarem en ella per jutjar si qualcuna se'n desvia
 *   molt.
 *
 * Per com estÃ  plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mÃ¨todes de classes que no estiguin ja importades. El que sÃ­ podeu fer Ã©s definir
 * tots els mÃ¨todes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a mÃ xim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuaciÃ³ els vostres noms i entregau Ãºnicament aquest fitxer.
 * - Nom 1: Santiago Rattenbach Paliza-BartolomÃ©
 * - Nom 2: Albert Salom Vanrell
 * - Nom 3:
 *
 * L'entrega es farÃ  a travÃ©s d'una tasca a l'Aula Digital abans de la data que se us hagui
 * comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir mÃ©s fÃ cilment
 * les actualitzacions amb enunciats nous. Si no podeu visualitzar bÃ© algun enunciat, assegurau-vos
 * que el vostre editor de texte estigui configurat amb codificaciÃ³ UTF-8.
 */
class Entrega {
  /*
   * AquÃ­ teniu els exercicis del Tema 1 (LÃ²gica).
   *
   * Els mÃ¨todes reben de parÃ metre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` Ã©s un element de
   * l'univers, podeu fer-ho com `p.test(x)`, tÃ© com resultat un booleÃ . Els predicats de dues
   * variables sÃ³n de tipus `BiPredicate<Integer, Integer>` i similarment s'avaluen com
   * `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposiciÃ³ donada Ã©s certa (suposau que l'univers Ã©s suficientment
   * petit com per utilitzar la forÃ§a bruta)
   */
  static class Tema1 {
    /*
     * Ã‰s cert que âˆ€x,y. P(x,y) -> Q(x) ^ R(y) ?
     */
    static boolean exercici1(
        int[] universe,
        BiPredicate<Integer, Integer> p,
        Predicate<Integer> q,
        Predicate<Integer> r) {
        for(int x:universe) {
            for (int y:universe) {
                if(p.test(x,y)) {
                    if(!(q.test(x)&&r.test(y))) {
                        return false;
                    }  
                }  
            }
        }
      return true;
    }

    /*
     * Ã‰s cert que âˆƒ!x. âˆ€y. Q(y) -> P(x) ? ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
        int condicionesNoCumplidas,xcorrectos=0;
        for(int x:universe) {
            condicionesNoCumplidas=0;
            for (int y:universe) {
                if(q.test(y)) {
                    if(!p.test(x)) {
                        condicionesNoCumplidas++;
                    }  
                }  
            }
            if(condicionesNoCumplidas==0) {
                xcorrectos++;           
            }
        }
        if(xcorrectos==1) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * És cert que ¬(?x. ?y. y ? x) ?
     *  ?x:?y: ¬(y ? x)
     * Observau que els membres de l'univers sÃ³n arrays, tractau-los com conjunts i podeu suposar
     * que cada un d'ells estÃ  ordenat de menor a major.
     */
    static boolean exercici3(int[][] universe) {
        int condicionesNoCumplidas,xcorrectos=0;
        for(int [] y:universe) {
            condicionesNoCumplidas=0;
            for (int [] x:universe) {
                boolean [] existeEnY=new boolean[x.length];
                for(int xn:x) {
                    for(int yn:y) {
                        if(yn==xn) {
                            existeEnY[xn]=true;
                        }
                    }
                }
                for(int i:y) {
                    if (!existeEnY[i]) {
                        condicionesNoCumplidas++;
                    }
                }
            }
            if(condicionesNoCumplidas==0) {
                xcorrectos++;           
            }
        }
        if(xcorrectos>1) {
            return true;
        }else{
            return false;
        }
    }

    /*
     * Ã‰s cert que âˆ€x. âˆƒ!y. xÂ·y â‰¡ 1 (mod n) ?
     */
    static boolean exercici4(int[] universe, int n) {
      return false; // TO DO
    }

    /*
     * AquÃ­ teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // âˆ€x,y. P(x,y) -> Q(x) ^ R(y)

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
      // âˆƒ!x. âˆ€y. Q(y) -> P(x) ?

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
              x -> x % 2 == 0, // x Ã©s mÃºltiple de 2
              x -> x % 4 == 0  // x Ã©s mÃºltiple de 4
          )
      );

      // Exercici 3
      // Â¬(âˆƒx. âˆ€y. y âŠ† x) ?

      assertThat(
          exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {} })
      );

      assertThat(
          !exercici3(new int[][] { {1, 2}, {0, 3}, {1, 2, 3}, {}, {0, 1, 2, 3} })
      );

      // Exercici 4
      // Ã‰s cert que âˆ€x. âˆƒ!y. xÂ·y â‰¡ 1 (mod n) ?

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
   * AquÃ­ teniu els exercicis del Tema 2 (Conjunts).
   *
   * De la mateixa manera que al Tema 1, per senzillesa tractarem els conjunts com arrays (sense
   * elements repetits). Per tant, un conjunt de conjunts d'enters tendrÃ  tipus int[][].
   *
   * Les relacions tambÃ© les representarem com arrays de dues dimensions, on la segona dimensiÃ³
   * nomÃ©s tÃ© dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i tambÃ© donarem el conjunt on estÃ  definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant int[] a,
   * int[] b, i un objecte de tipus Function<Integer, Integer> que podeu avaluar com f.apply(x) (on
   * x Ã©s un enter d'a i el resultat f.apply(x) Ã©s un enter de b).
   */
  static class Tema2 {
    /*
     * Ã‰s `p` una particiÃ³ d'`a`?
     *
     * `p` Ã©s un array de conjunts, haureu de comprovar que siguin elements d'`a`. Podeu suposar que
     * tant `a` com cada un dels elements de `p` estÃ  ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] p) {
      return false; // TO DO
    }

    /*
     * Comprovau si la relaciÃ³ `rel` definida sobre `a` Ã©s un ordre parcial i que `x` n'Ã©s el mÃ­nim.
     *
     * Podeu soposar que `x` pertany a `a` i que `a` estÃ  ordenat de menor a major.
     */
    static boolean exercici2(int[] a, int[][] rel, int x) {
      return false; // TO DO
    }

    /*
     * Suposau que `f` Ã©s una funciÃ³ amb domini `dom` i codomini `codom`.  Trobau l'antiimatge de
     * `y` (ordenau el resultat de menor a major, podeu utilitzar `Arrays.sort()`). Podeu suposar
     * que `y` pertany a `codom` i que tant `dom` com `codom` tambÃ© estÃ n ordenats de menor a major.
     */
    static int[] exercici3(int[] dom, int[] codom, Function<Integer, Integer> f, int y) {
      return new int[]{}; // TO DO
    }

    /*
     * Suposau que `f` Ã©s una funciÃ³ amb domini `dom` i codomini `codom`.  Retornau:
     * - 3 si `f` Ã©s bijectiva
     * - 2 si `f` nomÃ©s Ã©s exhaustiva
     * - 1 si `f` nomÃ©s Ã©s injectiva
     * - 0 en qualsevol altre cas
     *
     * Podeu suposar que `dom` i `codom` estÃ n ordenats de menor a major. Per comoditat, podeu
     * utilitzar les constants definides a continuaciÃ³:
     */
    static final int NOTHING_SPECIAL = 0;
    static final int INJECTIVE = 1;
    static final int SURJECTIVE = 2;
    static final int BIJECTIVE = INJECTIVE + SURJECTIVE;

    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      return -1; // TO DO
    }

    /*
     * AquÃ­ teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `p` Ã©s una particiÃ³ d'`a`?

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
      // Ã©s `rel` definida sobre `a` d'ordre parcial i `x` n'Ã©s el mÃ­nim?

      ArrayList<int[]> divisibility = new ArrayList<int[]>();

      for (int i = 1; i < 8; i++) {
        for (int j = 1; j <= i; j++) {
          if (i % j == 0) {
            // i Ã©s mÃºltiple de j, Ã©s a dir, j|i
            divisibility.add(new int[] { i, j });
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
      // classificar la funciÃ³ en res/injectiva/exhaustiva/bijectiva

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
   * AquÃ­ teniu els exercicis del Tema 3 (AritmÃ¨tica).
   *
   */
  static class Tema3 {
    /*
     * Donat `a`, `b` retornau el mÃ xim comÃº divisor entre `a` i `b`.
     *
     * Podeu suposar que `a` i `b` sÃ³n positius.
     */
    static int exercici1(int a, int b) {
      return -1; // TO DO
    }

    /*
     * Es cert que `a``x` + `b``y` = `c` tÃ© soluciÃ³?.
     *
     * Podeu suposar que `a`, `b` i `c` sÃ³n positius.
     */
    static boolean exercici2(int a, int b, int c) {
      return false; // TO DO
    }

    /*
     * Quin es l'invers de `a` mÃ²dul `n`?
     *
     * Retornau l'invers sempre entre 1 i `n-1`, en cas que no existeixi retornau -1
     */
    static int exercici3(int a, int n) {
      return -1; // TO DO
    }

    /*
     * AquÃ­ teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
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
      // `a``x` + `b``y` = `c` tÃ© soluciÃ³?

      assertThat(
              exercici2(4,2,2)
      );
      assertThat(
              !exercici2(6,2,1)
      );
      // Exercici 3
      // invers de `a` mÃ²dul `n`
      assertThat(exercici3(2, 5) == 3);
      assertThat(exercici3(2, 6) == -1);
    }
  }

  /*
   * Aquest mÃ¨tode `main` contÃ© alguns exemples de parÃ metres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i tambÃ© en podeu afegir d'altres (no els tendrem en
   * compte, perÃ² Ã©s molt recomanable).
   *
   * Podeu aprofitar el mÃ¨tode `assertThat` per comprovar fÃ cilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
  }

  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
