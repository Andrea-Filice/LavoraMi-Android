package com.andreafilice.lavorami;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StationDB {

    public static List<MetroStation> getStationsM1() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Sesto 1° Maggio FS", 45.54156, 9.23835, "Main", "M1"));
        stations.add(new MetroStation("Sesto Rondò", 45.5342, 9.23128, "Main", "M1"));
        stations.add(new MetroStation("Sesto Marelli", 45.52356, 9.22796, "Main", "M1"));
        stations.add(new MetroStation("Villa S. Giovanni", 45.51745, 9.22613, "Main", "M1"));
        stations.add(new MetroStation("Precotto", 45.51215, 9.22449, "Main", "M1"));
        stations.add(new MetroStation("Gorla", 45.50662, 9.22287, "Main", "M1"));
        stations.add(new MetroStation("Turro", 45.50091, 9.22114, "Main", "M1"));
        stations.add(new MetroStation("Rovereto", 45.49578, 9.21957, "Main", "M1"));
        stations.add(new MetroStation("Pasteur", 45.49091, 9.21816, "Main", "M1"));
        stations.add(new MetroStation("Loreto", 45.48584, 9.21638, "Main", "M1"));
        stations.add(new MetroStation("Lima", 45.48027, 9.21085, "Main", "M1"));
        stations.add(new MetroStation("Porta Venezia", 45.47471, 9.20568, "Main", "M1"));
        stations.add(new MetroStation("Palestro", 45.47142, 9.20194, "Main", "M1"));
        stations.add(new MetroStation("San Babila", 45.46642, 9.19757, "Main", "M1"));
        stations.add(new MetroStation("Duomo", 45.46443, 9.18927, "Main", "M1"));
        stations.add(new MetroStation("Cordusio", 45.46539, 9.18635, "Main", "M1"));
        stations.add(new MetroStation("Cairoli", 45.4682, 9.18225, "Main", "M1"));
        stations.add(new MetroStation("Cadorna FN", 45.4682, 9.17588, "Main", "M1"));
        stations.add(new MetroStation("Conciliazione", 45.46749, 9.1663, "Main", "M1"));
        stations.add(new MetroStation("Pagano", 45.46828, 9.16077, "Main", "M1"));
        stations.add(new MetroStation("Buonarroti", 45.47054, 9.1552, "Rho", "M1"));
        stations.add(new MetroStation("Amendola", 45.47356, 9.15132, "Rho", "M1"));
        stations.add(new MetroStation("Lotto", 45.47909, 9.14454, "Rho", "M1"));
        stations.add(new MetroStation("QT8", 45.48625, 9.13743, "Rho", "M1"));
        stations.add(new MetroStation("Lampugnano", 45.4894, 9.12731, "Rho", "M1"));
        stations.add(new MetroStation("Uruguay", 45.49344, 9.12045, "Rho", "M1"));
        stations.add(new MetroStation("Bonola", 45.49703, 9.11009, "Rho", "M1"));
        stations.add(new MetroStation("San Leonardo", 45.50116, 9.10149, "Rho", "M1"));
        stations.add(new MetroStation("Molino Dorino", 45.50516, 9.09323, "Rho", "M1"));
        stations.add(new MetroStation("Pero", 45.50869, 9.08581, "Rho", "M1"));
        stations.add(new MetroStation("Rho Fiera", 45.51797, 9.08564, "Rho", "M1"));
        stations.add(new MetroStation("Wagner", 45.46784, 9.15529, "Bisceglie", "M1"));
        stations.add(new MetroStation("De Angeli", 45.46656, 9.14987, "Bisceglie", "M1"));
        stations.add(new MetroStation("Gambara", 45.46499, 9.14295, "Bisceglie", "M1"));
        stations.add(new MetroStation("Bande Nere", 45.46133, 9.13695, "Bisceglie", "M1"));
        stations.add(new MetroStation("Primaticcio", 45.45952, 9.12961, "Bisceglie", "M1"));
        stations.add(new MetroStation("Inganni", 45.45756, 9.12225, "Bisceglie", "M1"));
        stations.add(new MetroStation("Bisceglie", 45.45531, 9.11335, "Bisceglie", "M1"));
        return stations;
    }

    public static List<MetroStation> getStationsM2() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Assago Forum", 45.40183, 9.14562, "Assago", "M2"));
        stations.add(new MetroStation("Assago Milanofiori Nord", 45.40945, 9.15004, "Assago", "M2"));
        stations.add(new MetroStation("P.Za Abbiategrasso", 45.42984, 9.17838, "Abbiategrasso", "M2"));
        stations.add(new MetroStation("Famagosta", 45.43719, 9.16795, "Main", "M2"));
        stations.add(new MetroStation("Romolo", 45.44373, 9.16767, "Main", "M2"));
        stations.add(new MetroStation("Porta Genova FS", 45.45273, 9.16972, "Main", "M2"));
        stations.add(new MetroStation("S. Agostino", 45.45834, 9.16977, "Main", "M2"));
        stations.add(new MetroStation("S. Ambrogio", 45.46185, 9.17325, "Main", "M2"));
        stations.add(new MetroStation("Cadorna FN", 45.4682, 9.17588, "Main", "M2"));
        stations.add(new MetroStation("Lanza", 45.47196, 9.18273, "Main", "M2"));
        stations.add(new MetroStation("Moscova", 45.4775, 9.18471, "Main", "M2"));
        stations.add(new MetroStation("Garibaldi FS", 45.48351, 9.18671, "Main", "M2"));
        stations.add(new MetroStation("Gioia", 45.48462, 9.19523, "Main", "M2"));
        stations.add(new MetroStation("Centrale FS", 45.48469, 9.20274, "Main", "M2"));
        stations.add(new MetroStation("Caiazzo", 45.48525, 9.2091, "Main", "M2"));
        stations.add(new MetroStation("Loreto", 45.48584, 9.21638, "Main", "M2"));
        stations.add(new MetroStation("Piola", 45.48081, 9.22509, "Main", "M2"));
        stations.add(new MetroStation("Lambrate FS", 45.48423, 9.235, "Main", "M2"));
        stations.add(new MetroStation("Udine", 45.49145, 9.23688, "Main", "M2"));
        stations.add(new MetroStation("Cimiano", 45.50004, 9.24142, "Main", "M2"));
        stations.add(new MetroStation("Crescenzago", 45.50521, 9.24822, "Main", "M2"));
        stations.add(new MetroStation("Cascina Gobba", 45.51114, 9.26052, "Main", "M2"));
        stations.add(new MetroStation("Cologno Sud", 45.52021, 9.27492, "Cologno", "M2"));
        stations.add(new MetroStation("Cologno Centro", 45.52747, 9.28296, "Cologno", "M2"));
        stations.add(new MetroStation("Cologno Nord", 45.53426, 9.29111, "Cologno", "M2"));
        stations.add(new MetroStation("Vimodrone", 45.51574, 9.28564, "Gessate", "M2"));
        stations.add(new MetroStation("Cascina Burrona", 45.51736, 9.29783, "Gessate", "M2"));
        stations.add(new MetroStation("Cernusco Sul Naviglio", 45.52097, 9.33083, "Gessate", "M2"));
        stations.add(new MetroStation("Villa Fiorita", 45.5205, 9.34609, "Gessate", "M2"));
        stations.add(new MetroStation("Cassina De Pecchi", 45.52163, 9.36213, "Gessate", "M2"));
        stations.add(new MetroStation("Bussero", 45.52541, 9.3758, "Gessate", "M2"));
        stations.add(new MetroStation("Villa Pompea", 45.52778, 9.38505, "Gessate", "M2"));
        stations.add(new MetroStation("Gorgonzola", 45.53649, 9.4036, "Gessate", "M2"));
        stations.add(new MetroStation("Cascina Antonietta", 45.5421, 9.42364, "Gessate", "M2"));
        stations.add(new MetroStation("Gessate", 45.54524, 9.43656, "Gessate", "M2"));
        return stations;
    }

    public static List<MetroStation> getStationsM3() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("San Donato", 45.42897, 9.25653, "Main", "M3"));
        stations.add(new MetroStation("Rogoredo FS", 45.43366, 9.23849, "Main", "M3"));
        stations.add(new MetroStation("Porto Di Mare", 45.43717, 9.23041, "Main", "M3"));
        stations.add(new MetroStation("Corvetto", 45.4403, 9.22385, "Main", "M3"));
        stations.add(new MetroStation("Brenta", 45.44301, 9.21801, "Main", "M3"));
        stations.add(new MetroStation("Lodi TIBB", 45.44698, 9.20975, "Main", "M3"));
        stations.add(new MetroStation("Porta Romana", 45.45152, 9.203, "Main", "M3"));
        stations.add(new MetroStation("Crocetta", 45.45597, 9.19569, "Main", "M3"));
        stations.add(new MetroStation("Missori", 45.46057, 9.18818, "Main", "M3"));
        stations.add(new MetroStation("Duomo", 45.46443, 9.18927, "Main", "M3"));
        stations.add(new MetroStation("Montenapoleone", 45.47027, 9.19249, "Main", "M3"));
        stations.add(new MetroStation("Turati", 45.47455, 9.19481, "Main", "M3"));
        stations.add(new MetroStation("Repubblica", 45.48038, 9.19878, "Main", "M3"));
        stations.add(new MetroStation("Centrale FS", 45.48469, 9.20274, "Main", "M3"));
        stations.add(new MetroStation("Sondrio", 45.4896, 9.20109, "Main", "M3"));
        stations.add(new MetroStation("Zara", 45.49225, 9.19246, "Main", "M3"));
        stations.add(new MetroStation("Maciachini", 45.49788, 9.18478, "Main", "M3"));
        stations.add(new MetroStation("Dergano", 45.50564, 9.17978, "Main", "M3"));
        stations.add(new MetroStation("Affori Centro", 45.51358, 9.17417, "Main", "M3"));
        stations.add(new MetroStation("Affori FN", 45.52087, 9.16901, "Main", "M3"));
        stations.add(new MetroStation("Comasina", 45.52835, 9.16387, "Main", "M3"));
        return stations;
    }

    public static List<MetroStation> getStationsM4() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Linate Aereoporto", 45.46284, 9.27791, "Main", "M4"));
        stations.add(new MetroStation("Repetti", 45.46201, 9.2404, "Main", "M4"));
        stations.add(new MetroStation("Stazione Forlanini", 45.46465, 9.2363, "Main", "M4"));
        stations.add(new MetroStation("Argonne", 45.46815, 9.23119, "Main", "M4"));
        stations.add(new MetroStation("Susa", 45.4682, 9.22499, "Main", "M4"));
        stations.add(new MetroStation("Dateo", 45.46799, 9.21845, "Main", "M4"));
        stations.add(new MetroStation("Tricolore", 45.46793, 9.20868, "Main", "M4"));
        stations.add(new MetroStation("San Babila", 45.46642, 9.19757, "Main", "M4"));
        stations.add(new MetroStation("Sforza-Policlinico", 45.45874, 9.19433, "Main", "M4"));
        stations.add(new MetroStation("Santa Sofia", 45.45633, 9.18863, "Main", "M4"));
        stations.add(new MetroStation("Vetra", 45.4571, 9.18262, "Main", "M4"));
        stations.add(new MetroStation("De Amicis", 45.45909, 9.17721, "Main", "M4"));
        stations.add(new MetroStation("S. Ambrogio", 45.46182, 9.17321, "Main", "M4"));
        stations.add(new MetroStation("Coni Zugna", 45.45909, 9.1649, "Main", "M4"));
        stations.add(new MetroStation("California", 45.45755, 9.16005, "Main", "M4"));
        stations.add(new MetroStation("Bolivar", 45.45538, 9.15316, "Main", "M4"));
        stations.add(new MetroStation("Tolstoj", 45.45359, 9.14801, "Main", "M4"));
        stations.add(new MetroStation("Frattini", 45.45182, 9.14245, "Main", "M4"));
        stations.add(new MetroStation("Gelsomini", 45.4496, 9.13542, "Main", "M4"));
        stations.add(new MetroStation("Segneri", 45.44695, 9.13086, "Main", "M4"));
        stations.add(new MetroStation("San Cristoforo", 45.44256, 9.13008, "Main", "M4"));
        return stations;
    }

    public static List<MetroStation> getStationsM5() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Bignami", 45.52653, 9.21227, "Main", "M5"));
        stations.add(new MetroStation("Ponale", 45.52186, 9.20939, "Main", "M5"));
        stations.add(new MetroStation("Bicocca", 45.51429, 9.20549, "Main", "M5"));
        stations.add(new MetroStation("Ca'Granda", 45.50733, 9.20121, "Main", "M5"));
        stations.add(new MetroStation("Istria", 45.50163, 9.19792, "Main", "M5"));
        stations.add(new MetroStation("Marche", 45.49639, 9.19499, "Main", "M5"));
        stations.add(new MetroStation("Zara", 45.49225, 9.19246, "Main", "M5"));
        stations.add(new MetroStation("Isola", 45.48759, 9.19131, "Main", "M5"));
        stations.add(new MetroStation("Garibaldi FS", 45.48351, 9.18671, "Main", "M5"));
        stations.add(new MetroStation("Monumentale", 45.48532, 9.18098, "Main", "M5"));
        stations.add(new MetroStation("Cenisio", 45.48799, 9.17207, "Main", "M5"));
        stations.add(new MetroStation("Gerusalemme", 45.48469, 9.16696, "Main", "M5"));
        stations.add(new MetroStation("Domodossola FN", 45.48135, 9.1621, "Main", "M5"));
        stations.add(new MetroStation("Tre Torri", 45.47807, 9.15664, "Main", "M5"));
        stations.add(new MetroStation("Portello", 45.4814, 9.15045, "Main", "M5"));
        stations.add(new MetroStation("Lotto", 45.47909, 9.14454, "Main", "M5"));
        stations.add(new MetroStation("Segesta", 45.47909, 9.13734, "Main", "M5"));
        stations.add(new MetroStation("S. Siro Ippodromo", 45.47909, 9.12858, "Main", "M5"));
        stations.add(new MetroStation("S. Siro Stadio", 45.47909, 9.11857, "Main", "M5"));
        return stations;
    }

    public static List<MetroStation> getStationsS1() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Saronno", 45.62534, 9.03075, "Main", "S1"));
        stations.add(new MetroStation("Saronno Sud", 45.61235, 9.04557, "Main", "S1"));
        stations.add(new MetroStation("Caronno Pertusella", 45.5981, 9.05794, "Main", "S1"));
        stations.add(new MetroStation("Cesate", 45.5908, 9.06631, "Main", "S1"));
        stations.add(new MetroStation("Garbagnate Milanese", 45.58014, 9.08042, "Main", "S1"));
        stations.add(new MetroStation("Garbagnate Parco Delle Groane", 45.57108, 9.09097, "Main", "S1"));
        stations.add(new MetroStation("Bollate Nord", 45.55262, 9.11209, "Main", "S1"));
        stations.add(new MetroStation("Bollate Centro", 45.54289, 9.12283, "Main", "S1"));
        stations.add(new MetroStation("Novate Milanese", 45.533, 9.13238, "Main", "S1"));
        stations.add(new MetroStation("Milano Quarto Oggiaro", 45.51918, 9.14562, "Main", "S1"));
        stations.add(new MetroStation("Milano Bovisa", 45.50257, 9.15925, "Main", "S1"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S1"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S1"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S1"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S1"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S1"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S1"));
        stations.add(new MetroStation("Milano Rogoredo", 45.43386, 9.23911, "Main", "S1"));
        stations.add(new MetroStation("S. Donato Milanese", 45.41879, 9.25291, "Main", "S1"));
        stations.add(new MetroStation("Borgolombardo", 45.4045, 9.27046, "Main", "S1"));
        stations.add(new MetroStation("S. Giuliano Milanese", 45.39134, 9.28652, "Main", "S1"));
        stations.add(new MetroStation("Melegnano", 45.35647, 9.31936, "Main", "S1"));
        stations.add(new MetroStation("S. Zenone Al Lambro", 45.33783, 9.36165, "Main", "S1"));
        stations.add(new MetroStation("Tavazzano", 45.32641, 9.40282, "Main", "S1"));
        stations.add(new MetroStation("Lodi", 45.3092, 9.49776, "Main", "S1"));
        return stations;
    }

    public static List<MetroStation> getStationsS2() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Mariano Comense", 45.69358, 9.18141, "Main", "S2"));
        stations.add(new MetroStation("Cabiate", 45.67592, 9.16843, "Main", "S2"));
        stations.add(new MetroStation("Meda", 45.66242, 9.15886, "Main", "S2"));
        stations.add(new MetroStation("Seveso", 45.6483, 9.14018, "Main", "S2"));
        stations.add(new MetroStation("Cesano Maderno", 45.63073, 9.14209, "Main", "S2"));
        stations.add(new MetroStation("Boviso Masciago", 45.61199, 9.14128, "Main", "S2"));
        stations.add(new MetroStation("Varedo", 45.59555, 9.15341, "Main", "S2"));
        stations.add(new MetroStation("Palazzolo Milanese", 45.58107, 9.15667, "Main", "S2"));
        stations.add(new MetroStation("Paderno Dugnano", 45.56499, 9.16132, "Main", "S2"));
        stations.add(new MetroStation("Cormano Cusano Milanino", 45.54571, 9.17409, "Main", "S2"));
        stations.add(new MetroStation("Milano Bruzzano Parco Nord", 45.53374, 9.1728, "Main", "S2"));
        stations.add(new MetroStation("Milano Affori", 45.52131, 9.16743, "Main", "S2"));
        stations.add(new MetroStation("Milano Bovisa", 45.50257, 9.15925, "Main", "S2"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S2"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S2"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S2"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S2"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S2"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S2"));
        stations.add(new MetroStation("Milano Rogoredo", 45.43386, 9.23911, "Main", "S2"));
        return stations;
    }

    public static List<MetroStation> getStationsS3() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Milano Cadorna", 45.46843, 9.17553, "Main", "S3"));
        stations.add(new MetroStation("Milano Domodossola", 45.48089, 9.16224, "Main", "S3"));
        stations.add(new MetroStation("Milano Bovisa", 45.50257, 9.15925, "Main", "S3"));
        stations.add(new MetroStation("Milano Quarto Oggiaro", 45.51918, 9.14562, "Main", "S3"));
        stations.add(new MetroStation("Novate Milanese", 45.533, 9.13238, "Main", "S3"));
        stations.add(new MetroStation("Bollate Centro", 45.54289, 9.12283, "Main", "S3"));
        stations.add(new MetroStation("Bollate Nord", 45.55262, 9.11209, "Main", "S3"));
        stations.add(new MetroStation("Garbagnate Parco Delle Groane", 45.57108, 9.09097, "Main", "S3"));
        stations.add(new MetroStation("Garbagnate Milanese", 45.58014, 9.08042, "Main", "S3"));
        stations.add(new MetroStation("Cesate", 45.5908, 9.06631, "Main", "S3"));
        stations.add(new MetroStation("Caronno Pertusella", 45.5981, 9.05794, "Main", "S3"));
        stations.add(new MetroStation("Saronno Sud", 45.61235, 9.04557, "Main", "S3"));
        stations.add(new MetroStation("Saronno", 45.62534, 9.03075, "Main", "S3"));
        return stations;
    }

    public static List<MetroStation> getStationsS4() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Camnago - Lentate", 45.66837, 9.13328, "Main", "S4"));
        stations.add(new MetroStation("Seveso", 45.6483, 9.14018, "Main", "S4"));
        stations.add(new MetroStation("Cesano Maderno", 45.63073, 9.14209, "Main", "S4"));
        stations.add(new MetroStation("Boviso Masciago", 45.61199, 9.14128, "Main", "S4"));
        stations.add(new MetroStation("Varedo", 45.59555, 9.15341, "Main", "S4"));
        stations.add(new MetroStation("Palazzolo Milanese", 45.58107, 9.15667, "Main", "S4"));
        stations.add(new MetroStation("Paderno Dugnano", 45.56499, 9.16132, "Main", "S4"));
        stations.add(new MetroStation("Cormano Cusano Milanino", 45.54571, 9.17409, "Main", "S4"));
        stations.add(new MetroStation("Milano Bruzzano Parco Nord", 45.53374, 9.1728, "Main", "S4"));
        stations.add(new MetroStation("Milano Affori", 45.52131, 9.16743, "Main", "S4"));
        stations.add(new MetroStation("Milano Bovisa", 45.50257, 9.15925, "Main", "S4"));
        stations.add(new MetroStation("Milano Domodossola", 45.48089, 9.16224, "Main", "S4"));
        stations.add(new MetroStation("Milano Cadorna", 45.46843, 9.17553, "Main", "S4"));
        return stations;
    }

    public static List<MetroStation> getStationsS5() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Varese", 45.81625, 8.83295, "Main", "S5"));
        stations.add(new MetroStation("Gazzada Schianno Morazzone", 45.77868, 8.82473, "Main", "S5"));
        stations.add(new MetroStation("Castronno", 45.74525, 8.81051, "Main", "S5"));
        stations.add(new MetroStation("Albizzate - Solbiate Arno", 45.7233, 8.80628, "Main", "S5"));
        stations.add(new MetroStation("Cavaria", 45.69814, 8.80371, "Main", "S5"));
        stations.add(new MetroStation("Gallarate", 45.65974, 8.79853, "Main", "S5"));
        stations.add(new MetroStation("Busto Arsizio", 45.61593, 8.86589, "Main", "S5"));
        stations.add(new MetroStation("Legnano", 45.5937, 8.91087, "Main", "S5"));
        stations.add(new MetroStation("Canegrate", 45.56934, 8.92673, "Main", "S5"));
        stations.add(new MetroStation("Parabiago", 45.55261, 8.94651, "Main", "S5"));
        stations.add(new MetroStation("Vanzago - Pogliano", 45.52536, 8.99573, "Main", "S5"));
        stations.add(new MetroStation("Rho", 45.52411, 9.04355, "Main", "S5"));
        stations.add(new MetroStation("Rho FieraMilano", 45.52113, 9.0885, "Main", "S5"));
        stations.add(new MetroStation("Milano Certosa", 45.50683, 9.13593, "Main", "S5"));
        stations.add(new MetroStation("Milano Villapizzone", 45.50202, 9.15092, "Main", "S5"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S5"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S5"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S5"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S5"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S5"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S5"));
        stations.add(new MetroStation("Milano Forlanini", 45.46438, 9.23691, "Main", "S5"));
        stations.add(new MetroStation("Segrate", 45.48075, 9.29859, "Main", "S5"));
        stations.add(new MetroStation("Pioltello Limito", 45.48611, 9.32949, "Main", "S5"));
        stations.add(new MetroStation("Vigate", 45.49441, 9.37649, "Main", "S5"));
        stations.add(new MetroStation("Melzo", 45.50208, 9.4192, "Main", "S5"));
        stations.add(new MetroStation("Pozzuolo Martesana", 45.50852, 9.45631, "Main", "S5"));
        stations.add(new MetroStation("Trecella", 45.51283, 9.48068, "Main", "S5"));
        stations.add(new MetroStation("Cassano d'Adda", 45.51446, 9.51278, "Main", "S5"));
        stations.add(new MetroStation("Treviglio", 45.51531, 9.58864, "Main", "S5"));
        return stations;
    }

    public static List<MetroStation> getStationsS6() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Novara", 45.45111, 8.62451, "Main", "S6"));
        stations.add(new MetroStation("Trecate", 45.42866, 8.73938, "Main", "S6"));
        stations.add(new MetroStation("Magenta", 45.4681, 8.88073, "Main", "S6"));
        stations.add(new MetroStation("Corbetta - S. Stefano", 45.48122, 8.91811, "Main", "S6"));
        stations.add(new MetroStation("Vittuone - Arluno", 45.49093, 8.94733, "Main", "S6"));
        stations.add(new MetroStation("Pregana Milanese", 45.51011, 9.00279, "Main", "S6"));
        stations.add(new MetroStation("Rho", 45.52411, 9.04355, "Main", "S6"));
        stations.add(new MetroStation("Rho FieraMilano", 45.52113, 9.0885, "Main", "S6"));
        stations.add(new MetroStation("Milano Certosa", 45.50683, 9.13593, "Main", "S6"));
        stations.add(new MetroStation("Milano Villapizzone", 45.50202, 9.15092, "Main", "S6"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S6"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S6"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S6"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S6"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S6"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S6"));
        stations.add(new MetroStation("Milano Forlanini", 45.46438, 9.23691, "Main", "S6"));
        stations.add(new MetroStation("Segrate", 45.48075, 9.29859, "Main", "S6"));
        stations.add(new MetroStation("Pioltello Limito", 45.48611, 9.32949, "Main", "S6"));
        stations.add(new MetroStation("Vigate", 45.49441, 9.37649, "Main", "S6"));
        stations.add(new MetroStation("Melzo", 45.50208, 9.4192, "Main", "S6"));
        stations.add(new MetroStation("Pozzuolo Martesana", 45.50852, 9.45631, "Main", "S6"));
        stations.add(new MetroStation("Trecella", 45.51283, 9.48068, "Main", "S6"));
        stations.add(new MetroStation("Cassano d'Adda", 45.51446, 9.51278, "Main", "S6"));
        stations.add(new MetroStation("Treviglio", 45.51531, 9.58864, "Main", "S6"));
        return stations;
    }

    public static List<MetroStation> getStationsS7() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Lecco", 45.85637, 9.3934, "Main", "S7"));
        stations.add(new MetroStation("Valmadrera", 45.84641, 9.36801, "Main", "S7"));
        stations.add(new MetroStation("Civate", 45.83133, 9.35275, "Main", "S7"));
        stations.add(new MetroStation("Sala Al Barro - Galbiate", 45.82118, 9.35937, "Main", "S7"));
        stations.add(new MetroStation("Oggiono", 45.78891, 9.33758, "Main", "S7"));
        stations.add(new MetroStation("Molteno", 45.78085, 9.30171, "Main", "S7"));
        stations.add(new MetroStation("Costa Masnaga", 45.76347, 9.2849, "Main", "S7"));
        stations.add(new MetroStation("Cassano Nibionno Bulciago", 45.74621, 9.27996, "Main", "S7"));
        stations.add(new MetroStation("Renate - Veduggio", 45.7284, 9.27997, "Main", "S7"));
        stations.add(new MetroStation("Besana", 45.70274, 9.28303, "Main", "S7"));
        stations.add(new MetroStation("Villa Raverio", 45.69038, 9.26172, "Main", "S7"));
        stations.add(new MetroStation("Carate Calo'", 45.67642, 9.25213, "Main", "S7"));
        stations.add(new MetroStation("Triuggio - Ponte Albiate", 45.65892, 9.26682, "Main", "S7"));
        stations.add(new MetroStation("Macherio - Canonica", 45.64556, 9.2869, "Main", "S7"));
        stations.add(new MetroStation("Biassono - Lesmo Parco", 45.63221, 9.29829, "Main", "S7"));
        stations.add(new MetroStation("Buttafava", 45.62142, 9.3013, "Main", "S7"));
        stations.add(new MetroStation("Villasanta", 45.60336, 9.30516, "Main", "S7"));
        stations.add(new MetroStation("Monza Sobborghi", 45.5819, 9.28449, "Main", "S7"));
        stations.add(new MetroStation("Monza", 45.57797, 9.27289, "Main", "S7"));
        stations.add(new MetroStation("Sesto S. Giovanni", 45.54126, 9.23903, "Main", "S7"));
        stations.add(new MetroStation("Milano Greco Pirelli", 45.51288, 9.21416, "Main", "S7"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S7"));
        return stations;
    }

    public static List<MetroStation> getStationsS8() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Lecco", 45.85637, 9.3934, "Main", "S8"));
        stations.add(new MetroStation("Lecco Maggianico", 45.83102, 9.41205, "Main", "S8"));
        stations.add(new MetroStation("Calolziocorte Olginate", 45.80175, 9.42714, "Main", "S8"));
        stations.add(new MetroStation("Airuno", 45.75549, 9.42232, "Main", "S8"));
        stations.add(new MetroStation("Olgiate-Calco-Brivio", 45.72919, 9.40366, "Main", "S8"));
        stations.add(new MetroStation("Cernusco-Merate", 45.69503, 9.39692, "Main", "S8"));
        stations.add(new MetroStation("Osnago", 45.67873, 9.38711, "Main", "S8"));
        stations.add(new MetroStation("Carnate Usmate", 45.65318, 9.37493, "Main", "S8"));
        stations.add(new MetroStation("Arcore", 45.62377, 9.32292, "Main", "S8"));
        stations.add(new MetroStation("Monza", 45.57797, 9.27289, "Main", "S8"));
        stations.add(new MetroStation("Sesto S. Giovanni", 45.54126, 9.23903, "Main", "S8"));
        stations.add(new MetroStation("Milano Greco Pirelli", 45.51288, 9.21416, "Main", "S8"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S8"));
        return stations;
    }

    public static List<MetroStation> getStationsS9() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Saronno", 45.62534, 9.03075, "Main", "S9"));
        stations.add(new MetroStation("Saronno Sud", 45.61235, 9.04557, "Main", "S9"));
        stations.add(new MetroStation("Ceriano Laghetto - Solaro", 45.6249, 9.07936, "Main", "S9"));
        stations.add(new MetroStation("Ceriano Laghetto Groane", 45.62718, 9.1002, "Main", "S9"));
        stations.add(new MetroStation("Cesano Maderno Laghetto Groane", 45.63033, 9.12652, "Main", "S9"));
        stations.add(new MetroStation("Cesano Maderno", 45.63073, 9.14209, "Main", "S9"));
        stations.add(new MetroStation("Seveso Baruccana", 45.63807, 9.16292, "Main", "S9"));
        stations.add(new MetroStation("Seregno", 45.64609, 9.20302, "Main", "S9"));
        stations.add(new MetroStation("Desio", 45.62011, 9.21829, "Main", "S9"));
        stations.add(new MetroStation("Lissone - Muggiò", 45.60618, 9.23526, "Main", "S9"));
        stations.add(new MetroStation("Monza", 45.57797, 9.27289, "Main", "S9"));
        stations.add(new MetroStation("Sesto S. Giovanni", 45.54126, 9.23903, "Main", "S9"));
        stations.add(new MetroStation("Milano Greco Pirelli", 45.51288, 9.21416, "Main", "S9"));
        stations.add(new MetroStation("Milano Lambrate", 45.48475, 9.23678, "Main", "S9"));
        stations.add(new MetroStation("Milano Forlanini", 45.46438, 9.23691, "Main", "S9"));
        stations.add(new MetroStation("Milano Scalo Romana", 45.44585, 9.21303, "Main", "S9"));
        stations.add(new MetroStation("Milano Tibaldi - Bocconi", 45.44394, 9.18506, "Main", "S9"));
        stations.add(new MetroStation("Milano Romolo", 45.44335, 9.1675, "Main", "S9"));
        stations.add(new MetroStation("Milano S. Cristoforo", 45.44256, 9.13008, "Main", "S9"));
        stations.add(new MetroStation("Corsico", 45.43605, 9.10899, "Main", "S9"));
        stations.add(new MetroStation("Cesano Boscone", 45.43044, 9.09158, "Main", "S9"));
        stations.add(new MetroStation("Trezzano sul Naviglio", 45.42025, 9.0669, "Main", "S9"));
        stations.add(new MetroStation("Gaggiano", 45.40874, 9.03118, "Main", "S9"));
        stations.add(new MetroStation("Albairate - Vermezzo", 45.40435, 8.95822, "Main", "S9"));
        return stations;
    }

    public static List<MetroStation> getStationsS11() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Como S. Giovanni", 45.80901, 9.07279, "Main", "S11"));
        stations.add(new MetroStation("Como Camerlata", 45.78457, 9.0794, "Main", "S11"));
        stations.add(new MetroStation("Cucciago", 45.74081, 9.08206, "Main", "S11"));
        stations.add(new MetroStation("Cantù - Cermenate", 45.71623, 9.09948, "Main", "S11"));
        stations.add(new MetroStation("Carimate", 45.69718, 9.10737, "Main", "S11"));
        stations.add(new MetroStation("Camnago - Lentate", 45.66837, 9.13328, "Main", "S11"));
        stations.add(new MetroStation("Seregno", 45.64609, 9.20302, "Main", "S11"));
        stations.add(new MetroStation("Desio", 45.62011, 9.21829, "Main", "S11"));
        stations.add(new MetroStation("Lissone - Muggiò", 45.60618, 9.23526, "Main", "S11"));
        stations.add(new MetroStation("Monza", 45.57797, 9.27289, "Main", "S11"));
        stations.add(new MetroStation("Sesto S. Giovanni", 45.54126, 9.23903, "Main", "S11"));
        stations.add(new MetroStation("Milano Greco Pirelli", 45.51288, 9.21416, "Main", "S11"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S11"));
        stations.add(new MetroStation("Milano Villapizzone", 45.50202, 9.15092, "Main", "S11"));
        stations.add(new MetroStation("Milano Certosa", 45.50683, 9.13593, "Main", "S11"));
        stations.add(new MetroStation("Rho FieraMilano", 45.52113, 9.0885, "Main", "S11"));
        stations.add(new MetroStation("Rho", 45.52411, 9.04355, "Main", "S11"));
        return stations;
    }

    public static List<MetroStation> getStationsS12() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Cormano Cusano Milanino", 45.54571, 9.17409, "Main", "S12"));
        stations.add(new MetroStation("Milano Bovisa", 45.50257, 9.15925, "Main", "S12"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S12"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S12"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S12"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S12"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S12"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S12"));
        stations.add(new MetroStation("Milano Rogoredo", 45.43386, 9.23911, "Main", "S12"));
        stations.add(new MetroStation("S. Donato Milanese", 45.41879, 9.25291, "Main", "S12"));
        stations.add(new MetroStation("Borgolombardo", 45.4045, 9.27046, "Main", "S12"));
        stations.add(new MetroStation("S. Giuliano Milanese", 45.39134, 9.28652, "Main", "S12"));
        stations.add(new MetroStation("Melegnano", 45.35647, 9.31936, "Main", "S12"));
        return stations;
    }

    public static List<MetroStation> getStationsS13() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Pavia", 45.18878, 9.14488, "Main", "S13"));
        stations.add(new MetroStation("Certosa di Pavia", 45.25656, 9.1542, "Main", "S13"));
        stations.add(new MetroStation("Villamaggiore", 45.32047, 9.19051, "Main", "S13"));
        stations.add(new MetroStation("Pieve Emanuele", 45.33966, 9.20351, "Main", "S13"));
        stations.add(new MetroStation("Locate Triulzi", 45.35974, 9.22152, "Main", "S13"));
        stations.add(new MetroStation("Milano Rogoredo", 45.43386, 9.23911, "Main", "S13"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S13"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S13"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S13"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S13"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S13"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S13"));
        stations.add(new MetroStation("Milano Bovisa", 45.50257, 9.15925, "Main", "S13"));
        return stations;
    }

    public static List<MetroStation> getStationsS19() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Milano Rogoredo", 45.43386, 9.23911, "Main", "S19"));
        stations.add(new MetroStation("Milano Pta Vittoria", 45.45989, 9.22355, "Main", "S19"));
        stations.add(new MetroStation("Milano Dateo", 45.46799, 9.21845, "Main", "S19"));
        stations.add(new MetroStation("Milano Pta Venezia", 45.47633, 9.20709, "Main", "S19"));
        stations.add(new MetroStation("Milano Repubblica", 45.48034, 9.19888, "Main", "S19"));
        stations.add(new MetroStation("Milano Pta Garibaldi", 45.48449, 9.18737, "Main", "S19"));
        stations.add(new MetroStation("Milano Lancetti", 45.4949, 9.17637, "Main", "S19"));
        stations.add(new MetroStation("Milano Villapizzone", 45.50202, 9.15092, "Main", "S19"));
        stations.add(new MetroStation("Milano Certosa", 45.50683, 9.13593, "Main", "S19"));
        stations.add(new MetroStation("Rho FieraMilano", 45.52113, 9.0885, "Main", "S19"));
        stations.add(new MetroStation("Rho", 45.52411, 9.04355, "Main", "S19"));
        return stations;
    }

    public static List<MetroStation> getStationsS31() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Brescia", 45.53252, 10.21297, "Main", "S31"));
        stations.add(new MetroStation("Brescia Borgo S. Giovanni", 45.54333, 10.19076, "Main", "S31"));
        stations.add(new MetroStation("Brescia Violino", 45.54511, 10.16873, "Main", "S31"));
        stations.add(new MetroStation("Castegnato", 45.56608, 10.11827, "Main", "S31"));
        stations.add(new MetroStation("Paderno Franciacorta", 45.58691, 10.08725, "Main", "S31"));
        stations.add(new MetroStation("Passirano", 45.59021, 10.06359, "Main", "S31"));
        stations.add(new MetroStation("Bornato - Calino", 45.59035, 10.03298, "Main", "S31"));
        stations.add(new MetroStation("Borgonato - Adro", 45.62225, 10.02005, "Main", "S31"));
        stations.add(new MetroStation("Provaglio Timoline", 45.63504, 10.03538, "Main", "S31"));
        stations.add(new MetroStation("Iseo", 45.65657, 10.05003, "Main", "S31"));
        return stations;
    }

    public static List<MetroStation> getStationsS10() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation( "Biasca",  46.35198,  8.97416,  "Main","S10"));
        stations.add(new MetroStation( "Castione",  46.22363,  9.0415,  "Main","S10"));
        stations.add(new MetroStation( "Bellinzona",  46.19543,  9.02951,  "Main","S10"));
        stations.add(new MetroStation( "Giubiasco",  46.17381,  9.00359,  "Main","S10"));
        stations.add(new MetroStation( "Lugano",  46.00501,  8.94695,  "Main","S10"));
        stations.add(new MetroStation( "Lugano Paradiso",  45.98917,  8.94656,  "Main","S10"));
        stations.add(new MetroStation( "Melide",  45.95576,  8.94823,  "Main","S10"));
        stations.add(new MetroStation( "Maroggia - Melano",  45.93241,  8.97383,  "Main","S10"));
        stations.add(new MetroStation( "Capolago - Riva S. Vitale",  45.90285,  8.97889,  "Main","S10"));
        stations.add(new MetroStation( "Mendrisio S. Martino",  45.87721,  8.98308,  "Main","S10"));
        stations.add(new MetroStation( "Mendrisio",  45.8691,  8.97878,  "Main","S10"));
        stations.add(new MetroStation( "Balerna",  45.84681,  9.0051,  "Main","S10"));
        stations.add(new MetroStation( "Chiasso",  45.83217,  9.03175,  "Main","S10"));
        stations.add(new MetroStation( "Como S. Giovanni",  45.80901,  9.07279,  "Main","S10"));
        stations.add(new MetroStation( "Como Camerlata",  45.78457,  9.0794,  "Main","S10"));
        return stations;
    }

    public static List<MetroStation> getStationsS50() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation( "Biasca",  46.35198,  8.97416,  "Main","S50"));
        stations.add(new MetroStation( "Castione",  46.22363,  9.0415,  "Main","S50"));
        stations.add(new MetroStation( "Bellinzona",  46.19543,  9.02951,  "Main","S50"));
        stations.add(new MetroStation( "Giubiasco",  46.17381,  9.00359,  "Main","S50"));
        stations.add(new MetroStation( "Lugano",  46.00501,  8.94695,  "Main","S50"));
        stations.add(new MetroStation( "Lugano Paradiso",  45.98917,  8.94656,  "Main","S50"));
        stations.add(new MetroStation( "Melide",  45.95576,  8.94823,  "Main","S50"));
        stations.add(new MetroStation( "Maroggia - Melano",  45.93241,  8.97383,  "Main","S50"));
        stations.add(new MetroStation( "Capolago - Riva S. Vitale",  45.90285,  8.97889,  "Main","S50"));
        stations.add(new MetroStation( "Mendrisio S. Martino",  45.87721,  8.98308,  "Main","S50"));
        stations.add(new MetroStation( "Mendrisio",  45.8691,  8.97878,  "Main","S50"));
        stations.add(new MetroStation( "Stabio",  45.84971,  8.94394,  "Main","S50"));
        stations.add(new MetroStation( "Cantello Gaggiolo",  45.83781,  8.90739,  "Main","S50"));
        stations.add(new MetroStation( "Arcisate",  45.85857,  8.86291,  "Main","S50"));
        stations.add(new MetroStation( "Induno Olona",  45.84587,  8.83802,  "Main","S50"));
        stations.add(new MetroStation( "Varese",  45.81625,  8.83295,  "Main","S50"));
        stations.add(new MetroStation( "Gallarate",  45.65974,  8.79853,  "Main","S50"));
        stations.add(new MetroStation( "Busto Arsizio",  45.61593,  8.86589,  "Main","S50"));
        stations.add(new MetroStation( "Busto Arsizio Nord",  45.60617,  8.85139,  "Main","S50"));
        stations.add(new MetroStation( "Ferno - Lonate Pozzolo",  45.60854,  8.75525,  "Main","S50"));
        stations.add(new MetroStation( "Malpensa Aereoporto Terminal 1",  45.62714,  8.71129,  "Main","S50"));
        stations.add(new MetroStation( "Malpensa Aereoporto Terminal 2",  45.65013,  8.72133,  "Main","S50"));
        return stations;
    }

    public static List<MetroStation> getStationsS30() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation( "Cadenazzo",  46.15262,  8.94168,  "Main","S30"));
        stations.add(new MetroStation( "Quartino",  46.15129,  8.88732,  "Main","S30"));
        stations.add(new MetroStation( "Magadino - Vira",  46.14531,  8.85019,  "Main","S30"));
        stations.add(new MetroStation( "S. Nazzaro",  46.13519,  8.80704,  "Main","S30"));
        stations.add(new MetroStation( "Gerra",  46.12207,  8.78494,  "Main","S30"));
        stations.add(new MetroStation( "Ranzo - S. Abbondio",  46.11697,  8.7766,  "Main","S30"));
        stations.add(new MetroStation( "Pino - Tronzano",  46.09866,  8.73706,  "Main","S30"));
        stations.add(new MetroStation( "Maccagno",  46.04324,  8.73763,  "Main","S30"));
        stations.add(new MetroStation( "Colmegna",  46.02475,  8.75189,  "Main","S30"));
        stations.add(new MetroStation( "Luino",  45.9969,  8.73738,  "Main","S30"));
        stations.add(new MetroStation( "Porto Valtraglia",  45.95836,  8.67402,  "Main","S30"));
        stations.add(new MetroStation( "Calde'",  45.9457,  8.66324,  "Main","S30"));
        stations.add(new MetroStation( "Laveno Mombello",  45.90326,  8.6244,  "Main","S30"));
        stations.add(new MetroStation( "Sangiano",  45.87449,  8.63087,  "Main","S30"));
        stations.add(new MetroStation( "Besozzo",  45.84233,  8.66322,  "Main","S30"));
        stations.add(new MetroStation( "Travedona Biandronno",  45.80976,  8.69615,  "Main","S30"));
        stations.add(new MetroStation( "Ternate Varano Borghi",  45.78183,  8.70069,  "Main","S30"));
        stations.add(new MetroStation( "Mornago Cimbro",  45.72985,  8.73441,  "Main","S30"));
        stations.add(new MetroStation( "Besnate",  45.69549,  8.76125,  "Main","S30"));
        stations.add(new MetroStation( "Gallarate",  45.65974,  8.79853,  "Main","S30"));
        return stations;
    }

    public static List<MetroStation> getStationsS40() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation( "Como S. Giovanni",  45.80901,  9.07279,  "Main","S40"));
        stations.add(new MetroStation( "Chiasso",  45.83217,  9.03175,  "Main","S40"));
        stations.add(new MetroStation( "Balerna",  45.84681,  9.0051,  "Main","S40"));
        stations.add(new MetroStation( "Mendrisio",  45.8691,  8.97878,  "Main","S40"));
        stations.add(new MetroStation( "Stabio",  45.84971,  8.94394,  "Main","S40"));
        stations.add(new MetroStation( "Cantello Gaggiolo",  45.83781,  8.90739,  "Main","S40"));
        stations.add(new MetroStation( "Arcisate",  45.85857,  8.86291,  "Main","S40"));
        stations.add(new MetroStation( "Induno Olona",  45.84587,  8.83802,  "Main","S40"));
        stations.add(new MetroStation( "Varese",  45.81625,  8.83295,  "Main","S40"));
        return stations;
    }

    public static List<MetroStation> getStationsRE80() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation( "Locarno",  46.17264,  8.80166,  "Main","RE80"));
        stations.add(new MetroStation( "Minusio",  46.174,  8.81956,  "Main","RE80"));
        stations.add(new MetroStation( "Tenero",  46.1774,  8.85186,  "Main","RE80"));
        stations.add(new MetroStation( "Gordola",  46.17915,  8.86585,  "Main","RE80"));
        stations.add(new MetroStation( "Riazzino",  46.17532,  8.88625,  "Main","RE80"));
        stations.add(new MetroStation( "Cadenazzo",  46.15262,  8.94168,  "Main","RE80"));
        stations.add(new MetroStation( "S. Antonino",  46.16041,  8.97392,  "Main","RE80"));
        stations.add(new MetroStation( "Lugano",  46.00501,  8.94695,  "Main","RE80"));
        stations.add(new MetroStation( "Lugano Paradiso",  45.98917,  8.94656,  "Main","RE80"));
        stations.add(new MetroStation( "Melide",  45.95576,  8.94823,  "Main","RE80"));
        stations.add(new MetroStation( "Maroggia - Melano",  45.93241,  8.97383,  "Main","RE80"));
        stations.add(new MetroStation( "Capolago - Riva S. Vitale",  45.90285,  8.97889,  "Main","RE80"));
        stations.add(new MetroStation( "Mendrisio S. Martino",  45.87721,  8.98308,  "Main","RE80"));
        stations.add(new MetroStation( "Mendrisio",  45.8691,  8.97878,  "Main","RE80"));
        stations.add(new MetroStation( "Balerna",  45.84681,  9.0051,  "Main","RE80"));
        stations.add(new MetroStation( "Chiasso",  45.83217,  9.03175,  "Main","RE80"));
        stations.add(new MetroStation( "Como S. Giovanni",  45.80901,  9.07279,  "Main","RE80"));
        stations.add(new MetroStation( "Seregno",  45.64609,  9.20302,  "Main","RE80"));
        stations.add(new MetroStation( "Monza",  45.57797,  9.27289,  "Main","RE80"));
        stations.add(new MetroStation( "Milano Centrale",  45.48713,  9.20482,  "Main","RE80"));
        return stations;
    }

    public static List<MetroStation> getStationsMXP1() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation( "Malpensa Aereoporto Terminal 2",  45.65013,  8.72133,  "Main","MXP1"));
        stations.add(new MetroStation( "Malpensa Aereoporto Terminal 1",  45.62714,  8.71129,  "Main","MXP1"));
        stations.add(new MetroStation( "Busto Arsizio Nord",  45.60617,  8.85139,  "Main","MXP1"));
        stations.add(new MetroStation( "Saronno",  45.62534,  9.03075,  "Main","MXP1"));
        stations.add(new MetroStation( "Milano Bovisa",  45.50257,  9.15925,  "Main","MXP1"));
        stations.add(new MetroStation( "Milano Domodossola",  45.48089,  9.16224,  "Main","MXP1"));
        stations.add(new MetroStation( "Milano Cadorna",  45.46843,  9.17553,  "Main","MXP1"));
        return stations;
    }

    public static List<MetroStation> getStationsMXP2() {
        List<MetroStation> stations = new ArrayList<>();
        stations.add(new MetroStation("Malpensa Aereoporto Terminal 2",  45.65013,  8.72133,  "Main","MXP2"));
        stations.add(new MetroStation("Malpensa Aereoporto Terminal 1",  45.62714,  8.71129,  "Main","MXP2"));
        stations.add(new MetroStation( "Ferno - Lonate Pozzolo",  45.60854,  8.75525,  "Main","MXP2"));
        stations.add(new MetroStation( "Busto Arsizio Nord",  45.60617,  8.85139,  "Main","MXP2"));
        stations.add(new MetroStation( "Castellanza",  45.61056,  8.87547,  "Main","MXP2"));
        stations.add(new MetroStation( "Rescaldina",  45.62229,  8.94666,  "Main","MXP2"));
        stations.add(new MetroStation( "Saronno",  45.62534,  9.03075,  "Main","MXP2"));
        stations.add(new MetroStation( "Milano Bovisa",  45.50257,  9.15925,  "Main","MXP2"));
        stations.add(new MetroStation( "Milano Pta Garibaldi",  45.48449,  9.18737,  "Main","MXP2"));
        stations.add(new MetroStation( "Milano Centrale",  45.48713,  9.20482,  "Main","MXP2"));
        return stations;
    }

    public static List<InterchangeInfo> getBusInterchanges() {
        List<InterchangeInfo> interchanges = new ArrayList<>();
        interchanges.add(new InterchangeInfo(
                "Molino Dorino MM",
                new String[]{"M1", "z601", "z606", "z617", "z620", "z621", "z648", "z649"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Milano Cadorna FN",
                new String[]{"M1", "M2", "MXP", "R16", "R17", "R22", "R27", "RE1", "RE7", "S3", "S4"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Parabiago",
                new String[]{"z644", "z643"},
                "bus.fill"
        ));

        interchanges.add(new InterchangeInfo(
                "Rho FS",
                new String[]{"S5", "S6", "S11", "z616", "z618"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Busto Garolfo",
                new String[]{"z625", "z627", "z644", "z647", "z648", "z649"},
                "bus.fill"
        ));

        interchanges.add(new InterchangeInfo(
                "Legnano",
                new String[]{"z602", "z612", "z601", "z611", "z642", "z627"},
                "bus.fill"
        ));

        interchanges.add(new InterchangeInfo(
                "Bisceglie MM",
                new String[]{"M1", "z560", "k506"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Romolo FS",
                new String[]{"M2", "S9", "S19", "R31"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Santo Stefano Ticino - Corbetta",
                new String[]{"S6"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Magenta FS",
                new String[]{"S6", "RV", "z641", "z646", "z559"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Abbiategrasso V. Veneto",
                new String[]{"z551", "z552", "z553", "z555", "z556", "z560"},
                "bus.fill"
        ));

        interchanges.add(new InterchangeInfo(
                "Gorgonzola MM",
                new String[]{"M2", "z401", "z403"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Cernusco sul Naviglio MM",
                new String[]{"M2"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Gessate MM",
                new String[]{"M2", "z404", "z405", "z406", "z407"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "San Donato MM",
                new String[]{"M3", "z411", "z412", "z413", "z415", "z420", "k501", "k502", "k511", "k512", "k521", "k522", "k523", "k524", "k525"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Sesto San Giovanni FS",
                new String[]{"M1", "R13", "R14", "RE8", "S7", "S8", "S9", "S11", "z221", "z222", "z225", "z227"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Monza FS",
                new String[]{"R13", "R14", "RE8", "RE80", "S7", "S8", "S9", "S11", "z228"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Seregno FS",
                new String[]{"RE80", "S9", "S11", "z228", "z231", "z233"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Desio FS",
                new String[]{"RE80", "S9", "S11", "z250", "z251"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Crema FS",
                new String[]{"R6", "k503", "k505", "k506", "k507", "k512", "k520", "k521", "k522", "k523", "k524", "k525", "k601"},
                "bus.fill"
        ));

        interchanges.add(new InterchangeInfo(
                "Linate Aereoporto",
                new String[]{"M4", "k510"},
                "airplane.departure"
        ));

        interchanges.add(new InterchangeInfo(
                "Pavia FS",
                new String[]{"R34", "R35", "R36", "R37", "RE13", "S13", "Pavia 2", "Pavia 3", "Pavia 4", "Pavia 6", "Pavia 7"},
                "train.side.front.car"
        ));

        interchanges.add(new InterchangeInfo(
                "Famagosta MM",
                new String[]{"M2"},
                "tram.fill.tunnel"
        ));

        interchanges.add(new InterchangeInfo(
                "Cremona",
                new String[]{"k208", "k214"},
                "bus.fill"
        ));
        return interchanges;
    }

    public static List<InterchangeInfo> getInterchanges() {
        return Arrays.asList(
                new InterchangeInfo("Rho Fiera-Milano", new String[]{"S5", "S6", "S11", "AV", "M1"}, "lightrail"),
                new InterchangeInfo("Lotto", new String[]{"M1", "M5"}, "tram.fill.tunnel"),
                new InterchangeInfo("Cadorna FN", new String[]{"MXP1", "R16", "R17", "R22", "R27", "RE1", "RE7", "S3", "S4", "M1", "M2"}, "lightrail"),
                new InterchangeInfo("Duomo", new String[]{"M1", "M3"}, "tram.fill.tunnel"),
                new InterchangeInfo("San Babila", new String[]{"M1", "M4"}, "tram.fill.tunnel"),
                new InterchangeInfo("Porta Venezia", new String[]{"S1", "S2", "S5", "S6", "S12", "S13", "M1"}, "tram.fill.tunnel"),
                new InterchangeInfo("Loreto", new String[]{"M1", "M2"}, "tram.fill.tunnel"),
                new InterchangeInfo("Sesto 1° Maggio FS", new String[]{"R13", "R14", "RE8", "S7", "S8", "S9", "S11"}, "lightrail"),
                new InterchangeInfo("Romolo", new String[]{"R31", "S9", "S19"}, "lightrail"),
                new InterchangeInfo("S. Ambrogio", new String[]{"M2", "M4"}, "tram.fill.tunnel"),
                new InterchangeInfo("Porta Garibaldi", new String[]{"AV", "MXP", "R6", "R13", "R14", "R21", "R23", "R28", "RE2", "RE5", "RE6", "RE13", "S1", "S2", "S5", "S6", "S7", "S8", "S9", "S11", "S12", "S13", "M2", "M5"}, "lightrail"),
                new InterchangeInfo("Centrale FS", new String[]{"AV", "MXP", "R4", "R28", "RE2", "RE4", "RE6", "RE8", "RE11", "RE13", "RE80", "M2", "M3"}, "lightrail"),
                new InterchangeInfo("Lambrate FS", new String[]{"R4", "R6", "R34", "R38", "RE2", "RE6", "RE8", "RE11", "RE13", "S9", "M2"}, "lightrail"),
                new InterchangeInfo("Affori FN", new String[]{"R16", "S2", "S4", "M3"}, "lightrail"),
                new InterchangeInfo("Zara", new String[]{"M3", "M5"}, "tram.fill.tunnel"),
                new InterchangeInfo("Repubblica", new String[]{"S1", "S2", "S5", "S6", "S12", "S13", "M3"}, "lightrail"),
                new InterchangeInfo("Lodi TIBB", new String[]{"S9", "S19", "M3"}, "figure.walk"),
                new InterchangeInfo("Rogoredo FS", new String[]{"R34", "R38", "RE8", "RE11", "RE13", "S1", "S2", "S9", "S12", "S13", "S19", "M3"}, "lightrail"),
                new InterchangeInfo("San Cristoforo", new String[]{"R31", "S9", "S19", "M4"}, "lightrail"),
                new InterchangeInfo("Sforza - Policlinico", new String[]{"M3", "M4"}, "figure.walk"),
                new InterchangeInfo("Dateo", new String[]{"S1", "S2", "S5", "S6", "S12", "S13", "M4"}, "tram.fill.tunnel"),
                new InterchangeInfo("Stazione Forlanini", new String[]{"R34", "RE8", "S5", "S6", "S9"}, "lightrail"),
                new InterchangeInfo("Linate Aereoporto", new String[]{"Aereoporto", "M4"}, "airplane.departure"),
                new InterchangeInfo("Domodossola FN", new String[]{"R16", "R17", "R22", "R27", "RE1", "RE7", "S3", "S4", "MXP1", "M5"}, "tram.fill.tunnel"),
                new InterchangeInfo("Como S. Giovanni", new String[]{"S10", "S11", "S40", "RE80"}, "lightrail"),
                new InterchangeInfo("Mendrisio", new String[]{"S10", "S40", "S50", "RE80"}, "lightrail"),
                new InterchangeInfo("Biasca", new String[]{"S10", "S50"}, "lightrail"),
                new InterchangeInfo("Varese", new String[]{"S5", "S40", "S50"}, "lightrail"),
                new InterchangeInfo("Gallarate", new String[]{"S30", "S50"}, "lightrail"),
                new InterchangeInfo("Busto Arsizio Nord", new String[]{"S50", "MXP"}, "lightrail"),
                new InterchangeInfo("Milano Bovisa", new String[]{"S1", "S2", "S3", "S4", "S12", "S13", "MXP"}, "lightrail"),
                new InterchangeInfo("Saronno", new String[]{"S1", "S3", "MXP"}, "lightrail"),
                new InterchangeInfo("Monza", new String[]{"S7", "S8", "S9", "S11", "RE80"}, "lightrail")
        );
    }

    public static List<MetroStation> getAllStations() {
        List<MetroStation> allStations = new ArrayList<>();
        allStations.addAll(getStationsM1());
        allStations.addAll(getStationsM2());
        allStations.addAll(getStationsM3());
        allStations.addAll(getStationsM4());
        allStations.addAll(getStationsM5());
        allStations.addAll(getStationsS1());
        allStations.addAll(getStationsS2());
        allStations.addAll(getStationsS3());
        allStations.addAll(getStationsS4());
        allStations.addAll(getStationsS5());
        allStations.addAll(getStationsS6());
        allStations.addAll(getStationsS7());
        allStations.addAll(getStationsS8());
        allStations.addAll(getStationsS9());
        allStations.addAll(getStationsS11());
        allStations.addAll(getStationsS12());
        allStations.addAll(getStationsS13());
        allStations.addAll(getStationsS19());
        allStations.addAll(getStationsS31());
        allStations.addAll(getStationsS10());
        allStations.addAll(getStationsS30());
        allStations.addAll(getStationsS40());
        allStations.addAll(getStationsS50());
        allStations.addAll(getStationsRE80());
        allStations.addAll(getStationsMXP1());
        allStations.addAll(getStationsMXP2());
        return allStations;
    }

    public static int getLineColor(String nomeLinea) {
        switch (nomeLinea) {
            //S LINES
            case "S1":
                return R.color.S1;
            case "S2":
                return R.color.S2;
            case "S3":
                return R.color.S3;
            case "S4":
                return R.color.S4;
            case "S5":
                return R.color.S5;
            case "S6":
                return R.color.S6;
            case "S7":
                return R.color.S7;
            case "S8":
                return R.color.S8;
            case "S9":
                return R.color.S9;
            case "S11":
                return R.color.S11;
            case "S12":
                return R.color.S12;
            case "S13":
                return R.color.S13;
            case "S19":
                return R.color.S19;
            case "S31":
                return R.color.S31;
            case "AV":
                return R.color.AV;

            //TILO LINES
            case "S10":
                return R.color.S10;
            case "S30":
                return R.color.S30;
            case "S40":
                return R.color.S40;
            case "S50":
                return R.color.S50;
            case "RE80":
                return R.color.RE80;

            //METRO LINES
            case "M1":
                return R.color.M1;
            case "M2":
                return R.color.M2;
            case "M3":
                return R.color.M3;
            case "M4":
                return R.color.M4;
            case "M5":
                return R.color.M5;

            //OTHERS
            default:
                if (nomeLinea.contains("z") || nomeLinea.contains("k"))
                    return R.color.BUS;
                else if (nomeLinea.contains("Filobus"))
                    return R.color.FILOBUS;
                else if(nomeLinea.equals("RE80"))
                    return R.color.RE80;
                else if (nomeLinea.contains("R") && !nomeLinea.contains("RE"))
                    return R.color.REGIONAL;
                else if (nomeLinea.contains("RE"))
                    return R.color.RE;
                else if (nomeLinea.matches("^[1-9][0-9]?$"))
                    return R.color.TRAM;
                else if (nomeLinea.startsWith("P"))
                    return R.color.AUTOGUIDOVIE;
                else if(nomeLinea.startsWith("MXP"))
                    return R.color.MXP;
                else
                    return R.color.OTHER;
        }
    }
}