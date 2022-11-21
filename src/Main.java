import AES.AESCipher;
import AES.AESDecipher;
import utils.HashUtils;
import utils.StringUtils;

import java.math.BigInteger;
import java.util.Arrays;

/*
    Andrews define a
    a = 124325339146889384540494091080 // 30 primeiros dígitos de p, substituindo o último dígito por 0

    Andrews gera A
    A = g^a mod p
    A =  4DCED87A7829E6B9F3B4E60DBE801B2B375D971F1A8EED99877B5DCD9BC2F9C407B26F287B303BACF17990B872980B7C1ADE233DFD6DF1A15486EDBB37D26D0A040D01EBEDAF099E3F1DC9C1181FE1FCFAC406324126CD29886A93A672B6169079877F63FAAE402BC85777BE7647CF76C113371240CD465D781C578C9D33D7F5

    Andrews envia A para Avelino

    Avelino envia B para Andrews
    B = 7E3E5D1FF6CA407C3D7D99029355FD561AD30B4CAEB7D9D5A3751DE992F9757DA8614EF49CE3B21A67D4714B5811B3086AA9F70ABB3648310AADB119F20E916DBFBB13405FBB332301F1FAF2DCC62DDEF9DE45712FC2E94CC90379FBF169B7E7C4ECF829F0A12079E23E01F974E68390A8D745B920C20B20BA11A602B22A0FB1

    Andrews gera V
    V = B^a mod p

    Andrews gera S
    S = SHA256(V) // primeiros 128 bits
    S = EEC0FA91C906CBDC37C5CF6763279330

    Avelino envia mensagem para Andrews
    msg recebida = DFC3D6896924B5358176D9C1B70D424A083562E7E3D40E3BB40568A178D06B5BFB4355F58EE282DB8DA13828455723CBED46A64B21C909A2D99C51ABE42CAD431226CA0475F0A5780482F81635A234855F633EAE81BD5E99F86DD7B02D0AF81AA1D71DB7802487863BB9FCE3EAEFED44

    Andrews decifra texto recebido
    msg recebida em texto claro = Show. Agora inverte esta mensagem e me envia ela de volta cifrada com a mesma senha

    Andrews inverte, cifra e envia msg para Avelino
    msg = 464CA90282D2E51033C40B011C3EB395FF7FE23F7D90DA0B0F3CAF550AF116D6D02BA079662103974A7D8611C0ABE2475F67C3422976ECEFA05BC74AB78B4B7C760D4B51B096C12C0B3BC64AB3BCF61D36B30C3FE60377F0547ECC647FC70566861DD15FDC34DBB28C927AEB0C4A2195

    Avelino envia instruções finais do trabalho
 */

public class Main {

    public static void main(String[] args) {
        // ETAPA 1
        System.out.println("ETAPA 1:");

        //      Passo 1 - Gerar A
        final String pHexadecimal = "B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9DCA52D23B616073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD7098488E9C219A73724EFFD6FAE5644738FAA31A4FF55BCCC0A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4371";
        final String gHexadecimal = "A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507FD6406CFF14266D31266FEA1E5C41564B777E690F5504F213160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1909D0D2263F80A76A6A24C087A091F531DBF0A0169B6A28AD662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24855E6EEB22B3B2E5";
        final BigInteger a = new BigInteger("124325339146889384540494091080");
        final String aHexadecimal = StringUtils.byteArrayToHexString(a.toByteArray());

        final BigInteger p = new BigInteger(pHexadecimal, 16);
        final BigInteger g = new BigInteger(gHexadecimal, 16);

        // Gera A. A=g^a mod p
        final BigInteger A = g.modPow(a, p);

        final String AHexadecimal = StringUtils.byteArrayToHexString(A.toByteArray());
        System.out.println("a: " + aHexadecimal);
        System.out.println("A: " + AHexadecimal);

        //      Passo 2 - Gerar V
        final String BHexadecimal = "7E3E5D1FF6CA407C3D7D99029355FD561AD30B4CAEB7D9D5A3751DE992F9757DA8614EF49CE3B21A67D4714B5811B3086AA9F70ABB3648310AADB119F20E916DBFBB13405FBB332301F1FAF2DCC62DDEF9DE45712FC2E94CC90379FBF169B7E7C4ECF829F0A12079E23E01F974E68390A8D745B920C20B20BA11A602B22A0FB1";
        final BigInteger B = new BigInteger(BHexadecimal, 16);

        // Gera V. V = B^a mod p
        final BigInteger V = B.modPow(a, p);

        //      Passo 3 - Gerar S
        byte[] S = HashUtils.hashSha256(V.toByteArray());

        // Copia os 16 primeiros bytes, ou seja, primeiros 128 bits de S
        byte[] chave = Arrays.copyOfRange(S, 0, 16);

        // Converte chave para hexadecimal, pois o encoder e decoder esperam a chave em hexadecimal
        final String chaveHexadecimal = StringUtils.byteArrayToHexString(chave);

        System.out.println("128 primeiros bits de S em hexadecimal: " + chaveHexadecimal);

        // ETAPA 2
        System.out.println("\n\nETAPA 2");

        //      Passo 1
        final String mensagemRecebidaCifradaHexadecimal = "DFC3D6896924B5358176D9C1B70D424A083562E7E3D40E3BB40568A178D06B5BFB4355F58EE282DB8DA13828455723CBED46A64B21C909A2D99C51ABE42CAD431226CA0475F0A5780482F81635A234855F633EAE81BD5E99F86DD7B02D0AF81AA1D71DB7802487863BB9FCE3EAEFED44";

        try {
            // Decifra mensgem recebida
            final String mensagemRecebida = AESDecipher.decode(chaveHexadecimal, mensagemRecebidaCifradaHexadecimal);

            System.out.println("Mensagem Recebida: " + mensagemRecebida);

            // Inverte o texto recebido
            final String mensagemInvertida = StringUtils.reverseString(mensagemRecebida);

            // Converte para mensgaem para hexadecimal, pois o encoder espera um texto em hexadecimal
            final String mensagemInvertidaHexadecimal = StringUtils.stringToHexString(mensagemInvertida);

            // Cifra mensagem para envio
            final String mensagemInvertidaCifradaHexadecimal = AESCipher.encode(chaveHexadecimal, mensagemInvertidaHexadecimal);

            System.out.println("Mensagem invertida cifrada em hexadecimal: " + mensagemInvertidaCifradaHexadecimal);

            // Recebe instruções finais
            final String mensagemRecebidaCifradaHexadecimal2 = "ED567C3701DB530ABCAEB253A7DF079D7D92187C09D9A48E2CCE9FD8BEF6DDF98B17CEF30362D4A8A81BB415F3D04A279C471BB460385E447DB9E1A2E83DE5B86DEBB3949604864814AA584E440DC3C73EAC6E054ABD6B1011210CC0AD794F1F04CCBF765FBF46A437F2FDFE369A4B6300757D276AFC0FB5C364C322F1E44C40";

            // Decira instruções finais
            final String mensagemRecebida2 = AESDecipher.decode(chaveHexadecimal, mensagemRecebidaCifradaHexadecimal2);

            System.out.println("Última mensagem recebida: " + mensagemRecebida2);

        } catch (Exception e) {
            System.out.println("Falha ao decifrar mensagem recebida");
            throw new RuntimeException(e);
        }
    }
}
