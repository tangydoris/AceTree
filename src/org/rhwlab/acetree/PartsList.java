package org.rhwlab.acetree;

import java.util.Hashtable;
import java.util.Arrays;

public class PartsList {

	static String partslist =
			"AB	P0.a	Embryonic founder cell\n"+
			"ADAL	ABplapaaaapp	Ring interneurons\n"+
			"ADAR	ABprapaaaapp	Ring interneurons\n"+
			"ADEL	ABplapaaaapa	Anterior deirid, sensory neuron, dopaminergic\n"+
			"ADER	ABprapaaaapa	Anterior deirid, sensory neuron, dopaminergic\n"+
			"ADEshL	ABarppaaaa	Anterior deirid sheath\n"+
			"ADEshR	ABarpppaaa	Anterior deirid sheath\n"+
			"ADFL	ABalpppppaa	Amphid neuron, prob. chemosensory\n"+
			"ADFR	ABpraaappaa	Amphid neuron, prob. chemosensory\n"+
			"ADLL	ABalppppaad	Amphid neuron, prob. chemosensory\n"+
			"ADLR	ABpraaapaad	Amphid neuron, prob. chemosensory\n"+
			"AFDL	ABalpppapav	Amphid finger cell, neuron associated with amphid sheath\n"+
			"AFDR	ABpraaaapav	Amphid finger cell, neuron associated with amphid sheath\n"+
			"AIAL	ABplppaappa	Amphid interneuron\n"+
			"AIAR	ABprppaappa	Amphid interneuron\n"+
			"AIBL	ABplaapappa	Amphid interneuron\n"+
			"AIBR	ABpraapappa	Amphid interneuron\n"+
			"AIML	ABplpaapppa	Ring interneuron\n"+
			"AIMR	ABprpaapppa	Ring interneuron\n"+
			"AINL	ABalaaaalal	Ring interneuron\n"+
			"AINR	ABalaapaaar	Ring interneuron\n"+
			"AIYL	ABplpapaaap	Amphid interneuron\n"+
			"AIYR	ABprpapaaap	Amphid interneuron\n"+
			"AIZL	ABplapaaapav	Amphid interneuron\n"+
			"AIZR	ABprapaaapav	Amphid interneuron\n"+
			"ALA	ABalapppaaa	Neuron, sends processes laterally and along dorsal cord\n"+
			"ALML	ABarppaappa	Anterior lateral microtubule cell, touch receptor\n"+
			"ALMR	ABarpppappa	Anterior lateral microtubule cell, touch receptor\n"+
			"ALNL	ABplapappppap	Neuron associated with ALM\n"+
			"ALNR	ABprapappppap	Neuron associated with ALM\n"+
			"AMshL	ABplaapaapp	Amphid sheath\n"+
			"AMshR	ABpraapaapp	Amphid sheath\n"+
			"AMsoL	ABplpaapapa	Amphid socket\n"+
			"AMsoR	ABprpaapapa	Amphid socket\n"+
			"AQR	QR.ap	ABprapapaaaap. Neuron, basal body. not part of a sensillum, projects into ring\n"+
			"AS1	P1.apa	ABplapaappapa or ABprapaappapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS2	P2.apa	ABprapaappapa or ABplapaappapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS3	P3.apa	ABplappaaaapa or ABprappaaaapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS4	P4apa	ABprappaaaapa or ABplappaaaapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS5	P5.apa	ABplappaapapa or ABprappaapapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS6	P6.apa	ABprappaapapa or ABplappaapapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS7	P7.apa	ABplappappapa or ABprappappapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS8	P8.apa	ABprappappapa or ABplappappapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS9	P9.apa	ABplapapapapa or ABprapapapapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS10	P10.apa	ABprapapapapa or ABplapapapapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"AS11	P11.apa	ABplapappaapa. Ventral cord motor neuron, innervates dorsal muscles, no ventral counterpart\n"+
			"ASEL	ABalppppppaa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASER	ABpraaapppaa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASGL	ABplaapapap	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASGR	ABpraapapap	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASHL	ABplpaappaa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASHR	ABprpaappaa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASIL	ABplaapapppa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASIR	ABpraapapppa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASJL	ABalpppppppa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASJR	ABpraaappppa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASKL	ABalpppapppa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"ASKR	ABpraaaapppa	Amphid neurons, single ciliated endngs, probably chemo-sensory; project into ring via commissure from ventral ganglion, make diverse synaptic connections in ring neuropil\n"+
			"AUAL	ABalpppppppp	Neuron, process runs with amphid processes but lacks cillialted ending\n"+
			"AUAR	ABpraaappppp	Neuron, process runs with amphid processes but lacks cillialted ending\n"+
			"AVAL	ABalppaaapa	Ventral cord interneuron, synapses onto VA, DA, and AS motor neurons; formerly called alpha\n"+
			"AVAR	ABalaappapa	Ventral cord interneuron, synapses onto VA, DA, and AS motor neurons; formerly called alpha\n"+
			"AVBL	ABplpaapaap	Ventral cord interneuron, synapsesonto VB and DB motor neurons; formerly called beta\n"+
			"AVBR	ABprpaapaap	Ventral cord interneuron, synapsesonto VB and DB motor neurons; formerly called beta\n"+
			"AVDL	ABalaaapalr	Ventral cord interneuron, synapses onto VA, DA, AS motorneurons; formerly called delta\n"+
			"AVDR	ABalaaapprl	Ventral cord interneuron, synapses onto VA, DA, AS motorneurons; formerly called delta\n"+
			"AVEL	ABalpppaaaa	Ventral cord interneuron, like AVD but outputs restrict-ed to anterior cord\n"+
			"AVER	ABpraaaaaaa	Ventral cord interneuron, like AVD but outputs restrict-ed to anterior cord\n"+
			"AVFL/R	P1.aaaa	ABplapaappaaaa or ABprapaappaaaa. Interneuron, processes in ventral cord and ring, few synapses\n"+
			"AVFL/R	W.aaa	ABprapaapaaaa. Interneuron, processes in ventral cord and ring, few synapses\n"+
			"AVG	ABprpapppap	Ventral cord interneuron, few synapses\n"+
			"AVHL	ABalapaaaaa	Neuron, mainly postsynaptic in ventral cord and presynaptic in the ring\n"+
			"AVHR	ABalappapaa	Neuron, mainly postsynaptic in ventral cord and presynaptic in the ring\n"+
			"AVJL	ABalapapppa	Neuron, synapses like AVHL/R\n"+
			"AVJR	ABalapppppa	Neuron, synapses like AVHL/R\n"+
			"AVKL	ABplpapapap	Ring and ventral cord interneuron\n"+
			"AVKR	ABprpapapap	Ring and ventral cord interneuron\n"+
			"AVL	ABprpappaap	Ring and ventral cord interneuron, few synapses\n"+
			"AVM	QR.paa	ABprapapaaapaa. Anterior ventral microtubule cell, touch receptor\n"+
			"AWAL	ABplaapapaa	Amphid wing cells, neurons having ciliated sheet-like sensory endings closely associated with amphid sheath\n"+
			"AWAR	ABpraapapaa	Amphid wing cells, neurons having ciliated sheet-like sensory endings closely associated with amphid sheath\n"+
			"AWBL	ABalpppppap	Amphid wing cells, neurons having ciliated sheet-like sensory endings closely associated with amphid sheath\n"+
			"AWBR	ABpraaappap	Amphid wing cells, neurons having ciliated sheet-like sensory endings closely associated with amphid sheath\n"+
			"AWCL	ABplpaaaaap	Amphid wing cells, neurons having ciliated sheet-like sensory endings closely associated with amphid sheath\n"+
			"AWCR	ABprpaaaaap	Amphid wing cells, neurons having ciliated sheet-like sensory endings closely associated with amphid sheath\n"+
			"B	ABprppppapa	Rectal cell, postembryonic blast cell in male\n"+
			"BAGL	ABalppappap	Neuron, ciliated ending in head, no supporting cells, associated with ILso\n"+
			"BAGR	ABarappppap	Neuron, ciliated ending in head, no supporting cells, associated with ILso\n"+
			"BDUL	ABarppaappp	Neuron, process runs along excretory canal and into ring, unique darkly staining synaptic vesicles\n"+
			"BDUR	ABarpppappp	Neuron, process runs along excretory canal and into ring, unique darkly staining synaptic vesicles\n"+
			"C	P0.ppa	Embryonic founder cell\n"+
			"CA1	P3.aapa	ABplappaaaaapa or ABprappaaaaapa. Male specific cell, not reconstructed\n"+
			"CA2	P4aapa	ABprappaaaaapa or ABplappaaaaapa. Male specific cell, not reconstructed\n"+
			"CA3	P5.aapa	ABplappaapaapa or ABprappaapaapa. Male specific cell, not reconstructed\n"+
			"CA4	P6.aapa	ABprappaapaapa or ABplappaapaapa. Male specific neuron, innervates dorsal muscles\n"+
			"CA5	P7.aapa	ABplappappaapa or ABprappappaapa. Male specific neuron, innervates dorsal muscles\n"+
			"CA6	P8.aapa	ABprappappaapa or ABplappappaapa. Male specific neuron, innervates dorsal muscles\n"+
			"CA7	P9.aapa	ABplapapapaapa or ABprapapapaapa. Male specific neuron, innervates dorsal muscles\n"+
			"CA8	P10.aapa	ABprapapapaapa or ABplapapapaapa. Male specific cell in ventral cord, neuron-like but lacks synapses\n"+
			"CA9	P11.aapa	ABplapappaaapa. Male specific cell in ventral cord, neuron-like but lacks synapses\n"+
			"CANL	ABalapaaapa	Process runs along excretory canal, no synapses, essential for survival\n"+
			"CANR	ABalappappa	Process runs along excretory canal, no synapses, essential for survival\n"+
			"CEMDL	ABplaaaaaap	Male specific cephalic neurons (programmed cell death in hermaphrodite embryo) open to outside, possible function in male chemotaxis toward hermaphrodite.\n"+
			"CEMDR	ABarpapaaap	Male specific cephalic neurons (programmed cell death in hermaphrodite embryo) open to outside, possible function in male chemotaxis toward hermaphrodite.\n"+
			"CEMVL	ABplpaapapp	Male specific cephalic neurons (programmed cell death in hermaphrodite embryo) open to outside, possible function in male chemotaxis toward hermaphrodite.\n"+
			"CEMVR	ABprpaapapp	Male specific cephalic neurons (programmed cell death in hermaphrodite embryo) open to outside, possible function in male chemotaxis toward hermaphrodite.\n"+
			"CEPDL	ABplaaaaappa	Cephalic neurons, contain dopamine\n"+
			"CEPDR	ABarpapaappa	Cephalic neurons, contain dopamine\n"+
			"CEPVL	ABplpaappppa	Cephalic neurons, contain dopamine\n"+
			"CEPVR	ABprpaappppa	Cephalic neurons, contain dopamine\n"+
			"CEPshDL	ABarpaaaapp	Cephalic sheath, sheet-like processes envelop meuropil of the ring and part of ventral ganglion\n"+
			"CEPshDR	ABarpaaapap	Cephalic sheath, sheet-like processes envelop meuropil of the ring and part of ventral ganglion\n"+
			"CEPshVL	ABplpaaapap	Cephalic sheath, sheet-like processes envelop meuropil of the ring and part of ventral ganglion\n"+
			"CEPshVR	ABprpaaapap	Cephalic sheath, sheet-like processes envelop meuropil of the ring and part of ventral ganglion\n"+
			"CEPsoDL	ABalapapppp	Cephalic socket\n"+
			"CEPsoDR	ABalapppppp	Cephalic socket\n"+
			"CEPsoVL	ABalppaappp	Cephalic socket\n"+
			"CEPsoVR	ABalaapappp	Cephalic socket\n"+
			"CPO	P2.aap	ABprapaappaap. Male specific cell in ventral cord, not reconstructed\n"+
			"CP1	P3.aapp	ABplappaaaaapp or ABprappaaaaapp. Male specific cell in ventral cord, not reconstructed\n"+
			"CP2	P4aapp	ABprappaaaaapp or ABplappaaaaapp. Male specific cell in ventral cord, not reconstructed\n"+
			"CP3	P5.aapp	ABplappaapaapp or ABprappaapaapp. Male specific cell in ventral cord, not reconstructed\n"+
			"CP4	P6.aapp	ABprappaapaapp or ABplappaapaapp. Male specific motor neuron in ventral cord\n"+
			"CP5	P7.aapp	ABplappappaapp or ABprappappaapp. Male specific motor neuron in ventral cord\n"+
			"CP6	P8.aapp	ABprappappaapp or ABplappappaapp. Male specific motor neuron in ventral cord\n"+
			"CP7	P9.aapp	ABplapapapaapp or ABprapapapaapp. Male specific motor neuron in ventral cord\n"+
			"CP8	P10.aapp	ABprapapapaapp or ABplapapapaapp. Male specific interneuron, project into preanal ganglion\n"+
			"CP9	P11.aapp	ABplapappaaapp. Male specific interneuron, project into preanal ganglion\n"+
			"D	P0.pppa	Embryonic founder cell\n"+
			"DA1	ABprppapaap	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA2	ABplppapapa	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA3	ABprppapapa	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA4	ABplppapapp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA5	ABprppapapp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA6	ABplpppaaap	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA7	ABprpppaaap	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA8	ABprpapappp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DA9	ABplpppaaaa	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB1/3	ABplpaaaapp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB2	ABarappappa	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB3/1	ABprpaaaapp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB4	ABprpappapp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB5	ABplpapappp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB6	ABplppaappp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DB7	ABprppaappp	Ventral cord motor neurons, innervate dorsal muscles\n"+
			"DD1	ABplppappap	Ventral cord motor neurons, reciprocal inhibitors, chnage synaptic pattern during postembryonic development\n"+
			"DD2	ABprppappap	Ventral cord motor neurons, reciprocal inhibitors, chnage synaptic pattern during postembryonic development\n"+
			"DD3	ABplppapppa	Ventral cord motor neurons, reciprocal inhibitors, chnage synaptic pattern during postembryonic development\n"+
			"DD4	ABprppapppa	Ventral cord motor neurons, reciprocal inhibitors, chnage synaptic pattern during postembryonic development\n"+
			"DD5	ABplppapppp	Ventral cord motor neurons, reciprocal inhibitors, chnage synaptic pattern during postembryonic development\n"+
			"DD6	ABprppapppp	Ventral cord motor neurons, reciprocal inhibitors, chnage synaptic pattern during postembryonic development\n"+
			"DVA	ABprppppapp	Ring interneurons, cell bodies in dorsorectal ganglion\n"+
			"DVB	K.p	ABplpapppaap. Ring interneuron, cell body in dorsorectal ganglion, inervates rectal muscles\n"+
			"DVC	Caapaa	Ring interneurons, cell bodies in dorsorectal ganglion\n"+
			"DVE	B.ppap	ABprppppapappap. Male specific neuron, cell body in dorsorectal ganglion\n"+
			"DVF	B.ppppa	ABprppppapappppa. Male specific neuron, cell body in dorsorectal ganglion\n"+
			"DX1/2	F.lvda	ABplppppapplvda. Male specific neuron, darkly staining cell body in pre- anal ganglion, process penetrate basement membrane and contact muscles\n"+
			"DX1/2	F.rvda	ABplppppapprvda. Male specific neuron, darkly staining cell body in pre- anal ganglion, process penetrate basement membrane and contact muscles\n"+
			"DX3/4	U.laa	ABplppppapalaa. Male specific neuron, darkly staining cell body in pre- anal ganglion, process penetrate basement membrane and contact muscles\n"+
			"DX3/4	U.raa	ABplppppaparaa. Male specific neuron, darkly staining cell body in pre- anal ganglion, process penetrate basement membrane and contact muscles\n"+
			"E	P0.pap	Embryonic founder cell\n"+
			"EF1/2	F.lvdp	Male specific neuron, large cell body in preanal ganglion, synaptic inputs from ray neurons.\n"+
			"EF1/2	F.rvdp	Male specific neuron, large cell body in preanal ganglion, synaptic inputs from ray neurons.\n"+
			"EF3/4	U.lap	ABplppppapalap. Male specific neuron, large cell body in preanal ganglion, synaptic inputs from ray neurons.\n"+
			"EF3/4	U.rap	ABplppppaparap. Male specific neuron, large cell body in preanal ganglion, synaptic inputs from ray neurons.\n"+
			"F	ABplppppapp	Rectal cell, blast cell in male\n"+
			"FLPL	ABplapaaapad	Neuron, ciliated ending in head, no supporting cells, associated with ILso\n"+
			"FLPR	ABprapaaapad	Neuron, ciliated ending in head, no supporting cells, associated with ILso\n"+
			"GLRDL	MSaaaaaal	Set of six cells that form a thin cylindrical sheet between pharynx and ring neuropile; no chemical synapses, but gap junctions with muscle arms and RME motor neurons\n"+
			"GLRDR	MSaaaaaar	Set of six cells that form a thin cylindrical sheet between pharynx and ring neuropile; no chemical synapses, but gap junctions with muscle arms and RME motor neurons\n"+
			"GLRL	MSapaaaad	Set of six cells that form a thin cylindrical sheet between pharynx and ring neuropile; no chemical synapses, but gap junctions with muscle arms and RME motor neurons\n"+
			"GLRR	MSppaaaad	Set of six cells that form a thin cylindrical sheet between pharynx and ring neuropile; no chemical synapses, but gap junctions with muscle arms and RME motor neurons\n"+
			"GLRVL	MSapaaaav	Set of six cells that form a thin cylindrical sheet between pharynx and ring neuropile; no chemical synapses, but gap junctions with muscle arms and RME motor neurons\n"+
			"GLRVR	MSppaaaav	Set of six cells that form a thin cylindrical sheet between pharynx and ring neuropile; no chemical synapses, but gap junctions with muscle arms and RME motor neurons\n"+
			"G1	ABprpaaaapa	Postembryonic blast cell, excretory socket in embryo\n"+
			"G2	ABplapaapa	Postembryonic blast cell, excretory socket in L1, G2.p becomes socket later\n"+
			"HOL	ABplaaappa	Seam hypodermal cell\n"+
			"HOR	ABarpapppa	Seam hypodermal cell\n"+
			"H1L	ABplaaappp	Seam hypodermal cell, postembryonic blast cell\n"+
			"H1R	ABarpapppp	Seam hypodermal cell, postembryonic blast cell\n"+
			"H2L	ABarppaaap	Seam hyopdermal cell, postemb. blast cell, L1 deirid socket\n"+
			"H2R	ABarpppaap	Seam hyopdermal cell, postemb. blast cell, L1 deirid socket\n"+
			"HOA	P10.pppa	ABplapapappppa. Neuron of hook sensillum, receptor anterior to cloaca in male\n"+
			"HOB	P10.ppap	ABplapapapppap. Neuron of hook sensillum, receptor anterior to cloaca in male\n"+
			"HOsh	P10.ppppp	ABplapapapppppp. Sheath of hook sensillum, receptor anterior to cloaca in male\n"+
			"HOso	P10.ppaa	ABplapapapppaa. Socket of hook sensillum, receptor anterior to cloaca in male\n"+
			"HSNL	ABplapppappa	Herm. specific motor neurons (die in male embryo), innervate vulval muscles, serotonergic\n"+
			"HSNR	ABprapppappa	Herm. specific motor neurons (die in male embryo), innervate vulval muscles, serotonergic\n"+
			"I1L	ABalpapppaa	Pharyngeal interneurons: ant sensory, input from RIP\n"+
			"I1R	ABarapappaa	Pharyngeal interneurons: ant sensory, input from RIP\n"+
			"I2L	ABalpappaapa	Pharyngeal interneurons, ant sensory.\n"+
			"I2R	ABarapapaapa	Pharyngeal interneurons, ant sensory.\n"+
			"I3	MSaaaaapaa	Pharyngeal interneuron, ant sensory.\n"+
			"I4	MSaaaapaa	Pharyngeal interneuron.\n"+
			"I5	ABarapapapp	Pharyngeal interneuron, post sensory.\n"+
			"I6	MSpaaapaa	Pharyngeal interneuron, post sensory.\n"+
			"IL1DL	ABalapappaaa	Inner labial neuron\n"+
			"IL1DR	ABalappppaaa	Inner labial neuron\n"+
			"IL1L	ABalapaappaa	Inner labial neuron\n"+
			"IL1R	ABalaappppaa	Inner labial neuron\n"+
			"IL1VL	ABalppapppaa	Inner labial neuron\n"+
			"IL1VR	ABarapppppaa	Inner labial neuron\n"+
			"IL2DL	ABalapappap	Inner labial neuron\n"+
			"IL2DR	ABalappppap	Inner labial neuron\n"+
			"IL2L	ABalapaappp	Inner labial neuron\n"+
			"IL2R	ABalaappppp	Inner labial neuron\n"+
			"IL2VL	ABalppapppp	Inner labial neuron\n"+
			"IL2VR	ABarapppppp	Inner labial neuron\n"+
			"ILshDL	ABalaaaparr	Inner labial sheath\n"+
			"ILshDR	ABalaaappll	Inner labial sheath\n"+
			"ILshL	ABalaaaalpp	Inner labial sheath\n"+
			"ILshR	ABalaapaapp	Inner labial sheath\n"+
			"ILshVL	ABalppapaap	Inner labial sheath\n"+
			"ILshVR	ABarapppaap	Inner labial sheath\n"+
			"ILsoDL	ABplaapaaap	Inner labial socket\n"+
			"ILsoDR	ABpraapaaap	Inner labial socket\n"+
			"ILsoL	ABalaaapall	Inner labial socket\n"+
			"ILsoR	ABalaaapprr	Inner labial socket\n"+
			"ILsoVL	ABalppapapp	Inner labial socket\n"+
			"ILsoVR	ABarapppapp	Inner labial socket\n"+
			"K	ABplpapppaa	Rectal cell, postembryonic blast cell\n"+
			"K\'	ABplpapppap	Rectal cell\n"+
			"K.a	K.a	ABplpapppaaa. Rectal cell\n"+
			"LUAL	ABplpppaapap	Interneuron, short process in post ventral cord\n"+
			"LUAR	ABprpppaapap	Interneuron, short process in post ventral cord\n"+
			"M	MSapaap	Postembryonic mesoblast\n"+
			"M1	MSpaapaaa	Pharyngeal motorneurons\n"+
			"M2L	ABaraapappa	Pharyngeal motorneurons\n"+
			"M2R	ABaraappppa	Pharyngeal motorneurons\n"+
			"M3L	ABaraapappp	Pharyngeal sensory-motorneurons\n"+
			"M3R	ABaraappppp	Pharyngeal sensory-motorneurons\n"+
			"M4	MSpaaaaaa	Pharyngeal motorneurons\n"+
			"M5	MSpaaapap	Pharyngeal motorneurons\n"+
			"MCL	ABalpaaappp	Pharyngeal neurons that synapse onto marginal cells\n"+
			"MCR	ABarapaappp	Pharyngeal neurons that synapse onto marginal cells\n"+
			"MI	ABaraappaaa	Pharyngeal motor neuron/interneuron\n"+
			"MS	P0.paa	Embryonic founder cell\n"+
			"NSML	ABaraapapaav	Pharyngeal neurosecretory motorneuron, contain serotonin\n"+
			"NSMR	ABaraapppaav	Pharyngeal neurosecretory motorneuron, contain serotonin\n"+
			"OLLL	ABalppppapaa	Lateral outer labial neurons\n"+
			"OLLR	ABpraaapapaa	Lateral outer labial neurons\n"+
			"OLLshL	ABalpppaapd	Lateral outer labial sheath\n"+
			"OLLshR	ABpraaaaapd	Lateral outer labial sheath\n"+
			"OLLsoL	ABalapaaapp	Lateral outer labial socket\n"+
			"OLLsoR	ABalappappp	Lateral outer labial socket\n"+
			"OLQDL	ABalapapapaa	Quadrant outer labial neuron\n"+
			"OLQDR	ABalapppapaa	Quadrant outer labial neuron\n"+
			"OLQVL	ABplpaaappaa	Quadrant outer labial neuron\n"+
			"OLQVR	ABprpaaappaa	Quadrant outer labial neuron\n"+
			"OLQshDL	ABarpaaaapa	Quadrant outer labial sheath\n"+
			"OLQshDR	ABarpaaapaa	Quadrant outer labial sheath\n"+
			"OLQshVL	ABalpppaaap	Quadrant outer labial sheath\n"+
			"OLQshVR	ABpraaaaaap	Quadrant outer labial sheath\n"+
			"OLQsoDL	ABarpaaaaal	Quadrant outer labial socket\n"+
			"OLQsoDR	ABarpaaaaar	Quadrant outer labial socket\n"+
			"OLQsoVL	ABalppaaapp	Quadrant outer labial socket\n"+
			"OLQsoVR	ABalaappapp	Quadrant outer labial socket\n"+
			"P0	Z	Single cell zygote\n"+
			"P4	P0.pppp	Embryonic founder cell of germ line\n"+
			"P1/2L	ABplapaapp	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P1/2R	ABprapaapp	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P3/4L	ABplappaaa	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P3/4R	ABprappaaa	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P5/6L	ABplappaap	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P5/6R	ABprappaap	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P7/8L	ABplappapp	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P7/8R	ABprappapp	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P9/10L	ABplapapap	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P9/10R	ABprapapap	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P11	ABplapappa	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"P12	ABprapappa	Postembryonic blast cells for ventral cord motorneurons, ventral hypodermis, vulva, male preanal ganglion; ventral hypodermis in L1\n"+
			"PCAL	Y.plppd	ABprpppaaaaplppd. Sensory neuron of postcloacal sensilla in male\n"+
			"PCAR	Y.prppd	ABprpppaaaaprppd. Sensory neuron of postcloacal sensilla in male\n"+
			"PCBL	Y.plpa	ABprpppaaaaplpa. Neuron, ending is sheath of postcloacal sensilla in male\n"+
			"PCBR	Y.prpa	ABprpppaaaaprpa. Neuron, ending is sheath of postcloacal sensilla in male\n"+
			"PCCL	B.arpaaa	ABprppppapaarpaaa. Sensory neuron of postcloacal sensilla in male\n"+
			"PCCR	B.alpaaa	ABprppppapaalpaaa. Sensory neuron of postcloacal sensilla in male\n"+
			"PChL	Y.plaa	ABprpppaaaaplaa. Hypodermal cell of postcloacal sensilla in male\n"+
			"PChR	Y.praa	ABprpppaaaapraa. Hypodermal cell of postcloacal sensilla in male\n"+
			"PCshL	Y.plppv	ABprpppaaaaplppv. Sheath of postcloacal sensilla in male\n"+
			"PCshR	Y.prppv	ABprpppaaaaprppv. Sheath of postcloacal sensilla in male\n"+
			"PCsoL	Y.plap	ABprpppaaaaplap. Socket of postcloacal sensilla in male\n"+
			"PCsoR	Y.prap	ABprpppaaaaprap. Socket of postcloacal sensilla in male\n"+
			"PDA	ABprpppaaaa	Motor neuron, process in dorsal cord, same as Y cell in hermaphrodite, Y.a in male\n"+
			"PDB	P12.apa	ABprapappaapa. Motor neuron, process in dorsal cord, cell body in pre-anal ganglion\n"+
			"PDC	P11.papa	ABplapappapapa. Male specific interneuron, preanal ganglion\n"+
			"PDEL	V5L.paaa	ABplapapaappaaa. Neuron, dopaminergic of postderid sensillum\n"+
			"PDER	V5R.paaa	ABprapapaappaaa. Neuron, dopaminergic of postderid sensillum\n"+
			"PDEshL	V5L.papp	ABplapapaappapp. Sheath of PDE\n"+
			"PDEshR	V5R.papp	ABprapapaappapp. Sheath of PDE\n"+
			"PDEsoL	V5L.papa	ABplapapaappapa. Socket of PDE\n"+
			"PDEsoR	V5R.papa	ABprapapaappapa. Socket of PDE\n"+
			"PGA	P11.papp	ABplapappapapp. Male specific interneuron, preanal ganglion\n"+
			"PHAL	ABplpppaapp	Phasmid neurons, probably chemosensory\n"+
			"PHAR	ABprpppaapp	Phasmid neurons, probably chemosensory\n"+
			"PHBL	ABplapppappp	Phasmid neurons, probably chemosensory\n"+
			"PHRB	ABprapppappp	Phasmid neurons, probably chemosensory\n"+
			"PHCL	TL.pppaa	ABplappppppppaa. Neuron, striated rootlet in male, possibly sensory in tail spike\n"+
			"PHCR	TR.pppaa	ABprappppppppaa. Neuron, striated rootlet in male, possibly sensory in tail spike\n"+
			"PHshL	ABplpppapaa	Phasmid sheath\n"+
			"PHshR	ABprpppapaa	Phasmid sheath\n"+
			"PHso1L	TL.paa	ABplappppppaa. Phasmid sockets\n"+
			"PHso1R	TR.paa	ABprappppppaa. Phasmid sockets\n"+
			"PHso2L	TL.pap	ABplappppppap. Phasmid sockets\n"+
			"PHso2R	TR.pap	ABprappppppap. Phasmid sockets\n"+
			"PLML	ABplapappppaa	Posterior lateral microtubule cell, touch receptor\n"+
			"PLMR	ABprapappppaa	Posterior lateral microtubule cell, touch receptor\n"+
			"PLNL	TL.pppap	ABplappppppppap. Interneuron, associated with PLM\n"+
			"PLNR	TR.pppap	ABprappppppppap. Interneuron, associated with PLM\n"+
			"PQR	QL.ap	ABplapapaaaap. Neuron, basal body, not part of a sensillum, projects into preanal gangion\n"+
			"PVCL	ABplpppaapaa	Ventral cord interneuron, cell body in lumbar ganglion, synapses onto VB andDB motor neurons, formerly called delta.\n"+
			"PVCR	ABprpppaapaa	Ventral cord interneuron, cell body in lumbar ganglion, synapses onto VB andDB motor neurons, formerly called delta.\n"+
			"PVDL	V5L.paapa	ABplapapaappaapa. Neuron, lateral process adjacent to excretory canal\n"+
			"PVDR	V5R.paapa	ABprapapaappaapa. Neuron, lateral process adjacent to excretory canal\n"+
			"PVM	QL.paa	ABplapapaaapaa. Posterior ventral microtuble cell, touch receptor\n"+
			"PVNL	TL.appp	ABplapppppappp. Interneuron/motor neuron, post. vent. cord, few synapses\n"+
			"PVNR	TR.appp	ABprapppppappp. Interneuron/motor neuron, post. vent. cord, few synapses\n"+
			"PVPL	ABplppppaaa	Interneuron, cell body in lumbar ganglion, projects along ventral cord to nerve ring\n"+
			"PVPR	ABprppppaaa	Interneuron, cell body in lumbar ganglion, projects along ventral cord to nerve ring\n"+
			"PVQL	ABplapppaaa	Interneuron, projects along ventral cord to ring\n"+
			"PVQR	ABprapppaaa	Interneuron, projects along ventral cord to ring\n"+
			"PVR	Caappa	Interneuron, projects along ventral cord to ring\n"+
			"PVT	ABplpappppa	Interneuron, projects along ventral cord to ring\n"+
			"PVV	P11.paaa	ABplapappapaaa. Male specific motor neuron, ventral cord\n"+
			"PVWL	TL.ppa	ABplapppppppa. Interneuron, posterior ventral cord, few synapses\n"+
			"PVWR	TR.ppa	ABprapppppppa. Interneuron, posterior ventral cord, few synapses\n"+
			"PVX	P12.aap	ABprapappaaap. Male specific interneuron, postsynaptic in ring and ventral cord\n"+
			"PVY	P11.paap	ABplapappapaap.\n"+
			"PVZ	P10.ppppa	ABplapapapppppa or ABprapapapppppa. Male specific motor neuron, ventral cord\n"+
			"QL	ABplapapaaa	Postembryonic neuroblast, migrates anteriorly\n"+
			"QR	ABprapapaaa	Postembryonic neuroblast, migrates posteriorly\n"+
			"R1AL	V5L.pppppaaa, R1.aaa	ABplapapaappppppaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R1AR	V5R.pppppaaa, R1.aaa	ABprapapaappppppaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R1BL	V5L.pppppapa, R1.apa	ABplapapaappppppapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R1BR	V5R.pppppapa, R1.apa	ABprapapaappppppapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R1stL	V5L.pppppapp, R1.app	ABplapapaappppppapp. Male sensory rays, structural cell\n"+
			"R1stR	V5R.pppppapp, R1.app	ABprapapaappppppapp. Male sensory rays, structural cell\n"+
			"R2AL	V6L.papapaaa, R2.aaa	ABarppappppapapaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R2AR	V6R.papapaaa, R2.aaa	ABarpppppppapapaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R2BL	V6L.papapapa, R2.apa	ABarppappppapapapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R2BR	V6R.papapapa, R2.apa	ABarpppppppapapapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R2stL	V6L.papapapp, R2.app	ABarppappppapapapp. Male sensory rays, structural cell\n"+
			"R2stR	V6R.papapapp, R2.app	ABarpppppppapapapp. Male sensory rays, structural cell\n"+
			"R3AL	V6L.papppaaa, R3.aaa	ABarppappppapppaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R3AR	V6R.papppaaa, R3.aaa	ABarpppppppapppaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R3BL	V6L.papppapa, R3.apa	ABarppappppapppapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R3BR	V6R.papppapa, R3.apa	ABarpppppppapppapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R3stL	V6L.papppapp, R3.app	ABarppappppapppapp. Male sensory rays, structural cell\n"+
			"R3stR	V6R.papppapp, R3.app	ABarpppppppapppapp. Male sensory rays, structural cell\n"+
			"R4AL	V6L.pppapaaa, R4.aaa	ABarppappppppapaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R4AR	V6R.pppapaaa, R4.aaa	ABarpppppppppapaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R4BL	V6L.pppapapa, R4.apa	ABarppappppppapapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R4BR	V6R.pppapapa, R4.apa	ABarpppppppppapapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R4stL	V6L.pppapapp, R4.app	ABarppappppppapapp. Male sensory rays, structural cell\n"+
			"R4stR	V6R.pppapapp, R4.app	ABarpppppppppapapp. Male sensory rays, structural cell\n"+
			"R5AL	V6L.pppppaaa, R5.aaa	ABarppappppppppaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R5AR	V6R.pppppaaa, R5.aaa	ABarpppppppppppaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R5BL	V6L.pppppapa, R5.apa	ABarppappppppppapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R5BR	V6R.pppppapa, R5.apa	ABarpppppppppppapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R5stL	V6L.pppppapp, R5.app	ABarppappppppppapp. Male sensory rays, structural cell\n"+
			"R5stR	V6R.pppppapp, R5.app	ABarpppppppppppapp. Male sensory rays, structural cell\n"+
			"R6AL	V6L.ppppaaaa, R6.aaa	ABarppapppppppaaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R6AR	V6R.ppppaaaa, R6.aaa	ABarppppppppppaaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R6BL	V6L.ppppaapa, R6.apa	ABarppapppppppaapa. Male sensory rays, neuron, not darkly staining nor open to outside\n"+
			"R6BR	V6R.ppppaapa, R6.apa	ABarppppppppppaapa. Male sensory rays, neuron, not darkly staining nor open to outside\n"+
			"R6stL	V6L.ppppaapp, R6.app	ABarppapppppppaapp. Male sensory rays, structural cell\n"+
			"R6stR	V6R.ppppaapp, R6.app	ABarppppppppppaapp. Male sensory rays, structural cell\n"+
			"R7AL	TL.apappaaa, R7.aaa	ABplapppppapappaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R7AR	TR.apappaaa, R7.aaa	ABprapppppapappaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R7BL	TL.apappapa, R7.apa	ABplapppppapappapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R7BR	TR.apappapa, R7.apa	ABprapppppapappapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R7stL	TL.apappapp, R7.app	ABplapppppapappapp. Male sensory rays, structural cell\n"+
			"R7stR	TR.apappapp, R7.app	ABprapppppapappapp. Male sensory rays, structural cell\n"+
			"R8AL	TL.appaaaaa, R8.aaa	ABplapppppappaaaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R8AR	TR.appaaaaa, R8.aaa	ABprapppppappaaaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R8BL	TL.appaaapa, R8.apa	ABplapppppappaaapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R8BR	TR.appaaapa, R8.apa	ABprapppppappaaapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R8stL	TL.appaaapp, R8.app	ABplapppppappaaapp. Male sensory rays, structural cell\n"+
			"R8stR	TR.appaaapp, R8.app	ABprapppppappaaapp. Male sensory rays, structural cell\n"+
			"R9AL	TL.appapaaa, R9.aaa	ABplapppppappapaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R9AR	TR.appapaaa, R9.aaa	ABprapppppappapaaa. Male sensory rays, neuron, striated rootlet\n"+
			"R9BL	TL.appapapa, R9.apa	ABplapppppappapapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R9BR	TR.appapapa, R9.apa	ABprapppppappapapa. Male sensory rays, neuron, darkly staining tip, open to outside\n"+
			"R9stL	TL.appapapp, R9.app	ABplapppppappapapp. Male sensory rays, structural cell\n"+
			"R9stR	TR.appapapp, R9.app	ABprapppppappapapp. Male sensory rays, structural cell\n"+
			"RIAL	ABalapaapaa	Ring interneuron, many synapses\n"+
			"RIAR	ABalaapppaa	Ring interneuron, many synapses\n"+
			"RIBL	ABplpaappap	Ring interneuron\n"+
			"RIBR	ABprpaappap	Ring interneuron\n"+
			"RICL	ABplppaaaapp	Ring interneuron\n"+
			"RICR	ABprppaaaapp	Ring interneuron\n"+
			"RID	ABalappaapa	Ring interneuron, projects along dorsal cord\n"+
			"RIFL	ABplppapaaap	Ring interneuron\n"+
			"RIFR	ABprppapaaap	Ring interneuron\n"+
			"RIGL	ABplppappaa	Ring interneuron\n"+
			"RIGR	ABprppappaa	Ring interneuron\n"+
			"RIH	ABprpappaaa	Ring interneuron\n"+
			"RIML	ABplppaapap	Ring motor neuron\n"+
			"RIMR	ABprppaapap	Ring motor neuron\n"+
			"RIPL	ABalpapaaaa	Ring/pharynx interneuron, only direct connection between pharynx and ring\n"+
			"RIPR	ABprappaaaa	Ring/pharynx interneuron, only direct connection between pharynx and ring\n"+
			"RIR	ABprpapppaa	Ring interneuron\n"+
			"RIS	ABprpappapa	Ring interneuron\n"+
			"RIVL	ABplpaapaaa	Ring interneuron\n"+
			"RIVR	ABprpaapaaa	Ring interneuron\n"+
			"RMDDL	ABalpapapaa	Ring motor neuron/interneuron, many synapses\n"+
			"RMDDR	ABarappapaa	Ring motor neuron/interneuron, many synapses\n"+
			"RMDL	ABalpppapad	Ring motor neuron/interneuron, many synapses\n"+
			"RMDR	ABpraaaapad	Ring motor neuron/interneuron, many synapses\n"+
			"RMDVL	ABalppapaaa	Ring motor neuron/interneuron, many synapses\n"+
			"RMDVR	ABarapppaaa	Ring motor neuron/interneuron, many synapses\n"+
			"RMED	ABalapppaap	Ring motor neuron\n"+
			"RMEL	ABalaaaarlp	Ring motor neuron\n"+
			"RMER	ABalaaaarrp	Ring motor neuron\n"+
			"RMEV	ABplpappaaa	Ring motor neuron\n"+
			"RMFL	G2.al	ABplapaapaal. Ring motor neuron/interneuron\n"+
			"RMFR	G2.ar	ABplapaapaar. Ring motor neuron/interneuron\n"+
			"RMGL	ABplapaaapp	Ring interneuron\n"+
			"RMGR	ABprapaaapp	Ring interneuron\n"+
			"RMHL	G1.l	ABprpaaaapal. Ring motor neuron/interneuron\n"+
			"RMHR	G1.r	ABprpaaaapar. Ring motor neuron/interneuron\n"+
			"SAADL	ABalppapapa	Ring interneuron, anteriorly projecting process that runs sublaterally\n"+
			"SAADR	ABarapppapa	Ring interneuron, anteriorly projecting process that runs sublaterally\n"+
			"SAAVL	ABplpaaaaaa	Ring interneuron, anteriorly projecting process that runs sublaterally\n"+
			"SAAVR	ABprpaaaaaa	Ring interneuron, anteriorly projecting process that runs sublaterally\n"+
			"SABD	ABplppapaap	Ring interneuron, anteriorly projecting process that runs sublaterally, synapses to anterior body muscles in L1\n"+
			"SABVL	ABplppapaaaa	Ring interneuron, anteriorly projecting process that runs sublaterally, synapses to anterior body muscles in L1\n"+
			"SABVR	ABprppapaaaa	Ring interneuron, anteriorly projecting process that runs sublaterally, synapses to anterior body muscles in L1\n"+
			"SDQL	QL.pap	ABplapapaaapap. Post. lateral interneuron, process projects into ring\n"+
			"SDQR	QR.pap	ABprapapaaaoap. Ant. lateral interneuron, process projects into ring\n"+
			"SIADL	ABplpapaapa	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIADR	ABprpapaapa	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIAVL	ABplpapappa	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIAVR	ABprpapappa	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIBDL	ABplppaaaaa	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIBDR	ABprppaaaaa	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIBVL	ABplpapaapp	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SIBVR	ABprpapaapp	Receives a few synapses in the ring, has a posteriorly directed process that runs sublaterally\n"+
			"SMBDL	ABalpapapapp	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMBDR	ABarappapapp	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMBVL	ABalpapappp	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMBVR	ABarappappp	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMDDL	ABplpapaaaa	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMDDR	ABprpapaaaa	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMDVL	ABalppappaa	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SMDVR	ABarappppaa	Ring motor neuron/interneuron, has a posteriorly direct ed process that runs sublaterally\n"+
			"SPCL	B.alpaap	ABprppppapaalpaap. Male specific sensory /motor neuron, innervates spicule protractor muscle\n"+
			"SPCR	B.arpaap	ABprppppapaarpaap. Male specific sensory /motor neuron, innervates spicule protractor muscle\n"+
			"SPDL	B.alpapaa	ABprppppapaalpapaa. Sensory neuron of copulatory spicle in male, cilliated, open to outside at tip of spicule\n"+
			"SPDR	B.arpapaa	ABprppppapaarpapaa. Sensory neuron of copulatory spicle in male, cilliated, open to outside at tip of spicule\n"+
			"SPVL	B.al/raalda	ABprppppapaalaalda or ABprppppapaaraalda. Sensory neuron of copulatory spicle in male, cilliated, open to outside at tip of spicule\n"+
			"SPVR	B.al/raarda	ABprppppapaaraarda or ABprppppapaalaarda. Sensory neuron of copulatory spicle in male, cilliated, open to outside at tip of spicule\n"+
			"SPshDL	B.alpapap	ABprppppapaalpapap. Sheath of male copulatory spicule\n"+
			"SPshDR	B.arpapap	ABprppppapaarpapap. Sheath of male copulatory spicule\n"+
			"SPshVL	B.al/raaldp	ABprppppapaalaaldp or ABprppppapaaraaldp. Sheath of male copulatory spicule\n"+
			"SpshVR	B.al/raarda	ABprppppapaaraalda or ABprppppapaalaalda. Sheath of male copulatory spicule\n"+
			"SPso1L	B.al/rpppl	ABprppppapaalpppl or ABprppppapaarpppl. Socket of male copulatory spicule\n"+
			"SPso1R	B.al/rpppr	ABprppppapaalpppr or ABprppppapaarpppr. Socket of male copulatory spicule\n"+
			"SPso2L	B.al/raald	ABprppppapaalaald or ABprppppapaaraald. Socket of male copulatory spicule\n"+
			"SPso2R	B.al/raard	ABprppppapaalaard or ABprppppapaaraard. Socket of male copulatory spicule\n"+
			"SPso3L	B.al/raalv	ABprppppapaalaalv or ABprppppapaaraalv. Socket of male copulatory spicule\n"+
			"SPso3R	B.al/raarv	ABprppppapaalaarv or ABprppppapaaraarv. Socket of male copulatory spicule\n"+
			"SPso4L	B.alpapp	ABprppppapaalpapp. Socket of male copulatory spicule\n"+
			"SPso4r	B.arpapp	ABprppppapaarpapp. Socket of male copulatory spicule\n"+
			"TL	ABplappppp	Tail seam hypodermal cell, postembryonic blast cell, functions as phasmid socket in L1\n"+
			"TR	ABprappppp	Tail seam hypodermal cell, postembryonic blast cell, functions as phasmid socket in L1\n"+
			"U	ABplppppapa	Rectal cell, postembryonic blast cell in male\n"+
			"URADL	ABplaaaaaaa	Ring motor neuron\n"+
			"URADR	ABarpapaaaa	Ring motor neuron\n"+
			"URAVL	ABplpaaapaa	Ring motor neuron\n"+
			"URAVR	ABprpaaapaa	Ring motor neuron\n"+
			"URBL	ABplaapaapa	Neuron, presynaptic in ring, ending in head\n"+
			"URBR	ABpraapaapa	Neuron, presynaptic in ring, ending in head\n"+
			"URXL	ABplaaaaappp	Ring interneuron\n"+
			"URXR	ABarpapaappp	Ring interneuron\n"+
			"URYDL	ABalapapapp	Neuron, presynaptic in ring, ending in head\n"+
			"URYDR	ABalapppapp	Neuron, presynaptic in ring, ending in head\n"+
			"URYVL	ABplpaaappp	Neuron, presynaptic in ring, ending in head\n"+
			"URYVR	ABprpaaappp	Neuron, presynaptic in ring, ending in head\n"+
			"V1L	ABarppapaa	Seam hypodermal cell, postembryonic blast cell\n"+
			"V1R	ABarppppaa	Seam hypodermal cell, postembryonic blast cell\n"+
			"V2L	ABarppapap	Seam hypodermal cell, postembryonic blast cell\n"+
			"V2R	ABarppppap	Seam hypodermal cell, postembryonic blast cell\n"+
			"V3L	ABplappapa	Seam hypodermal cell, postembryonic blast cell\n"+
			"V3R	ABprappapa	Seam hypodermal cell, postembryonic blast cell\n"+
			"V4L	ABarppappa	Seam hypodermal cell, postembryonic blast cell\n"+
			"V4R	ABarpppppa	Seam hypodermal cell, postembryonic blast cell\n"+
			"V5L	ABplapapaap	Seam hypodermal cell, postembryonic blast cell\n"+
			"V5R	ABprapapaap	Seam hypodermal cell, postembryonic blast cell\n"+
			"V6L	ABarppappp	Seam hypodermal cell, postembryonic blast cell\n"+
			"V6R	ABarpppppp	Seam hypodermal cell, postembryonic blast cell\n"+
			"VA1	W.pa	ABprapaapapa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA2	P2.aaaa	ABprapaappaaaa or ABplapaappaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA3	P3.aaaa	ABplappaaaaaaa or ABprappaaaaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA4	P4aaaa	ABprappaaaaaaa or ABplappaaaaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA5	P5.aaaa	ABplappaapaaaa or ABprappaapaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA6	P6.aaaa	ABprappaapaaaa or ABplappaapaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA7	P7.aaaa	ABplappappaaaa or ABprappaapaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA8	P8.aaaa	ABprappaapaaaa or ABplappappaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA9	P9.aaaa	ABplapapapaaaa or ABprapapapaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA10	P10.aaaa	ABprapapapaaaa or ABplapapapaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA11	P11.aaaa	ABplapappaaaaa. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VA12	P12.aaaa	ABprapappaaaaa. Ventral cord motor neuron, innervates vent. body muscles, but also interneuron in preanal ganglion\n"+
			"VB1	P1.aaap	ABplapaappaaap or ABprapaappaaap. Ventral cord motor neuron, innervates vent. body muscles, also interneuron in ring\n"+
			"VB2	W.aap	ABprapaapaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB3	P2.aaap	ABprapaappaaap or ABplapaappaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB4	P3.aaap	ABplappaaaaaap or ABprappaaaaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB5	P4aaap	ABprappaaaaaap or ABplappaaaaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB6	P5.aaap	ABplappaapaaap or ABprappaapaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB7	P6.aaap	ABprappaapaaap or ABplappaapaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB8	P7.aaap	ABplappappaaap or ABprappaapaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB9	P8.aaap	ABprappaapaaap or ABplappappaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB10	P9.aaap	ABplapapapaaap or ABprapapapaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VB11	P10.aaap	ABprapapapaaap or ABplapapapaaap. Ventral cord motor neuron, innervates vent. body muscles\n"+
			"VC1	P3.aap	ABplappaaaaap or ABprappaaaaap. Hermaphrodite specific ventral cord motor neuron innervates vulval muscles and ventral body muscles\n"+
			"VC2	P4aap	ABprappaaaaap or ABplappaaaaap. Hermaphrodite specific ventral cord motor neuron innervates vulval muscles and ventral body muscles\n"+
			"VC3	P5.aap	ABplappaapaap or ABprappaapaap. Hermaphrodite specific ventral cord motor neuron innervates vulval muscles and ventral body muscles\n"+
			"VC4	P6.aap	ABprappaapaap or ABplappaapaap. Hermaphrodite specific ventral cord motor neuron innervates vulval muscles and ventral body muscles\n"+
			"VC5	P7.aap	ABplappappaap or ABprappaapaap. Hermaphrodite specific ventral cord motor neuron innervates vulval muscles and ventral body muscles\n"+
			"VC6	P8.aap	ABprappaapaap or ABplappappaap. Hermaphrodite specific ventral cord motor neuron innervates vulval muscles and ventral body muscles\n"+
			"VD1	W.pp	ABprapaapapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD2	P1.app	ABplapaappapp or ABprapaappapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD3	P2.app	ABprapaappapp or ABplapaappapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD4	P3.app	ABplappaaaapp or ABprappaaaapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD5	P4app	ABprappaaaapp or ABplappaaaapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD6	P5.app	ABplappaapapp or ABprappaapapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD7	P6.app	ABprappaapapp or ABplappaapapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD8	P7.app	ABplappappapp or ABprappaapapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD9	P8.app	ABprappaapapp or ABplappappapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD10	P9.app	ABplapapapapp or ABprapapapapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD11	P10.app	ABprapapapapp or ABplapapapapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD12	P11.app	ABplapappaapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"VD13	P12.app	ABprapappaapp. Ventral cord motor neuron, innervates ventral body muscles, probably reciprocal inhibitor\n"+
			"W	ABprapaapa	Postembryonic neuroblast, analogous to Pn.a cells\n"+
			"XXXL	ABplaaapaa	Embryonic head hypodermal cell\n"+
			"XXXR	ABarpappaa	Embryonic head hypodermal cell\n"+
			"Y	ABprpppaaaa	Rectal cell at hatching, becomes PDA in hermaphrodite, postembryonic blast cell in male\n"+
			"Z1	MSpppaap	Somatic gonad precursor cell\n"+
			"Z2	P0.ppppp	Germ line precursor cell\n"+
			"Z3	P0.ppppa	Germ line precursor cell\n"+
			"Z4	MSappaap	Somatic gonad precursor cell\n"+
			"Anterior_arcade_DR	ABaraaapppa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Anterior_arcade_DL	ABalpaappaa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Anterior_arcade_V	ABalpapaapa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Posterior_arcade_DL	ABalpaapaaa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Posterior_arcade_VL	ABalpaappap	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Posterior_arcade_DR	ABaraaapaaa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Posterior_arcade_D	ABaraaappaa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Posterior_arcade_VR	ABaraaappap	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"Posterior_arcade_V	ABarapapapa	Interface between pharynx and hypodermis, form anterior part of the buccal cavity\n"+
			"cc_DL	M.dlpa	Postembryonic coelomocyte in hermaphrodite\n"+
			"cc_DR	M.drpa	Postembryonic coelomocyte in hermaphrodite\n"+
			"cc_male_D	M.dlpappp	Single male postembryonic coelomocyte\n"+
			"ccAL	MSapapaaa	Embryonic coelomocytes\n"+
			"ccAR	MSppapaaa	Embryonic coelomocytes\n"+
			"ccPL	MSapapaap	Embryonic coelomocytes\n"+
			"ccPR	MSppapaap	Embryonic coelomocytes\n"+
			"e1D	ABaraaaapap	Pharyngeal epithelial cells\n"+
			"e1VL	ABaraaaaaaa	Pharyngeal epithelial cells\n"+
			"e1VR	ABaraaaaapa	Pharyngeal epithelial cells\n"+
			"e2DL	ABalpaapaap	Pharyngeal epithelial cells\n"+
			"e2DR	ABaraaapaap	Pharyngeal epithelial cells\n"+
			"e2V	ABalpappapa	Pharyngeal epithelial cells\n"+
			"e3D	ABaraapaaaa	Pharyngeal epithelial cells\n"+
			"e3VL	ABalpaaaaaa	Pharyngeal epithelial cells\n"+
			"e3VR	ABarapaaaaa	Pharyngeal epithelial cells\n"+
			"exc_cell	ABplpappaap	Large H-shaped excretory cell\n"+
			"Excretory canal R	ABplpappaap	Large H-shaped excretory cell\n"+
			"Excretory canal L	ABplpappaap	Large H-shaped excretory cell\n"+
			"exc_duct	ABplpaaaapa	Excretory duct\n"+
			"exc_gl_L	ABplpapapaa	Excretory glands, fused, send processes to ring,open into excretory duct\n"+
			"exc_gl_R	ABprpapapaa	Excretory glands, fused, send processes to ring,open into excretory duct\n"+
			"socket exc_socket	G2.p	Excretory socket, links duct to hypodermis\n"+
			"g1AL	MSaapaapaa	Pharyngeal gland cells\n"+
			"g1AR	MSpapaapaa	Pharyngeal gland cells\n"+
			"g1P	MSaaaaapap	Pharyngeal gland cells\n"+
			"g2L	MSaapapaa	Pharyngeal gland cells\n"+
			"g2R	MSpapapaa	Pharyngeal gland cells\n"+
			"hmc	MSappaaa	Head mesodermal cell, function unknown\n"+
			"hyp1	ABalpaapppa	Cylindrical hypodermal syncytium in head\n"+
			"hyp1	ABaraaapppp	Cylindrical hypodermal syncytium in head\n"+
			"hyp1	ABarappaapa	Cylindrical hypodermal syncytium in head\n"+
			"hyp2	ABalpaapppp	Cylindrical hypodermal syncytium in head\n"+
			"hyp2	ABalpapaaap	Cylindrical hypodermal syncytium in head\n"+
			"hyp3	ABplaapaaaa	Cylindrical hypodermal syncytium in head\n"+
			"hyp3	ABpraapaaaa	Cylindrical hypodermal syncytium in head\n"+
			"hyp4	ABarpapapa	Cylindrical hypodermal syncytium in head\n"+
			"hyp4	ABplaappaa	Cylindrical hypodermal syncytium in head\n"+
			"hyp4	ABpraappaa	Cylindrical hypodermal syncytium in head\n"+
			"hyp5	ABarpappap	Cylindrical hypodermal syncytium in head\n"+
			"hyp5	ABplaaapap	Cylindrical hypodermal syncytium in head\n"+
			"hyp6	ABarpaapaa	Cylindrical hypodermal syncytium in head\n"+
			"hyp6	ABarpapapp	Cylindrical hypodermal syncytium in head\n"+
			"hyp6	ABplaaaapa	Cylindrical hypodermal syncytium in head\n"+
			"hyp6	ABplaaaapp	Cylindrical hypodermal syncytium in head\n"+
			"hyp6	ABplaappap	Cylindrical hypodermal syncytium in head\n"+
			"hyp6	ABpraappap	Cylindrical hypodermal syncytium in head\n"+
			"hyp7	ABarpaapap	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABarpaappa	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABarpaappp	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABarppaapa	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABarpppapa	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABplaapppa	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABplaapppp	Embryonic large hypodermal syncytium\n"+
			"hyp7	AB.plappppa	Embryonic large hypodermal syncytium\n"+
			"hyp7	AB.praapppa	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABpraapppp	Embryonic large hypodermal syncytium\n"+
			"hyp7	ABprappppa	Embryonic large hypodermal syncytium\n"+
			"hyp7	Caaaaa	Embryonic large hypodermal syncytium\n"+
			"hyp7	Caaaap	Embryonic large hypodermal syncytium\n"+
			"hyp7	Caaapa	Embryonic large hypodermal syncytium\n"+
			"hyp7	Caaapp	Embryonic large hypodermal syncytium\n"+
			"hyp7	Caappd	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpaaaa	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpaaap	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpaapa	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpaapp	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpapaa	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpapap	Embryonic large hypodermal syncytium\n"+
			"hyp7	Cpappd	Embryonic large hypodermal syncytium\n"+
			"hyp8/9	ABplpppapap	Tail ventral hypodermis\n"+
			"hyp8/9	ABprpppapap	Tail ventral hypodermis\n"+
			"hyp10	ABplppppppp	Tail ventral hypodermis\n"+
			"hyp10	ABprppppppp	Tail ventral hypodermis\n"+
			"hyp11	Cpappa	Tail dorsal hypodermis\n"+
			"hyp12	P12.pa	Preanal hypodermis\n"+
			"hyp_hook	P10.papp	Hook\n"+
			"hyp_hook	P11.ppaa	Hypodermis associated with hook sensillum of male\n"+
			"hyp_hook	P11.ppap	Hypodermis associated with hook sensillum of male\n"+
			"hyp_hook	P11.ppp	Hypodermis associated with hook sensillum of male\n"+
			"int	Ealaad	Intestinal cell\n"+
			"int	Ealaav	Intestinal cell\n"+
			"int	Ealap	Intestinal cell\n"+
			"int	Ealpa	Intestinal cell\n"+
			"int	Ealpp	Intestinal cell\n"+
			"int	Earaad	Intestinal cell\n"+
			"int	Earaav	Intestinal cell\n"+
			"int	Earap	Intestinal cell\n"+
			"int	Earpa	Intestinal cell\n"+
			"int	Earpp	Intestinal cell\n"+
			"int	Eplaa	Intestinal cell\n"+
			"int	Eplap	Intestinal cell\n"+
			"int	Eplpa	Intestinal cell\n"+
			"int	Eplppa	Intestinal cell\n"+
			"int	Eplppp	Intestinal cell\n"+
			"int	Epraa	Intestinal cell\n"+
			"int	Eprap	Intestinal cell\n"+
			"int	Eprpa	Intestinal cell\n"+
			"int	Eprppa	Intestinal cell\n"+
			"int	Eprppp	Intestinal cell\n"+
			"int	In.a	Postembryonic intestinal cell\n"+
			"int	In.p	Postembryonic intestinal cell\n"+
			"linker_killer	U.lp or U.rp	One of these cells, sometimes fused with U.l/ra, phagocytoses the male linker cell\n"+
			"m1DL_of_pm1	ABaraapaaap	Pharyngeal muscle cell\n"+
			"m1DR_of_pm1	ABaraappaap	Pharyngeal muscle cell\n"+
			"m1L_of_pm1	ABaraaaaaap	Pharyngeal muscle cell\n"+
			"m1R_of_pm1	ABaraaaaapp	Pharyngeal muscle cell\n"+
			"m1VL_of_pm1	ABalpaaaapa	Pharyngeal muscle cell\n"+
			"m1VR_of_pm1	ABaraaaaapa	Pharyngeal muscle cell\n"+
			"m2DL_of_pm2	ABaraapaapa	Pharyngeal muscle cell\n"+
			"m2DR_of_pm2	ABaraappapa	Pharyngeal muscle cell\n"+
			"m2L_of_pm2	ABalpaaapaa	Pharyngeal muscle cell\n"+
			"m2R_of_pm2	ABarapaapaa	Pharyngeal muscle cell\n"+
			"m2VL_of_pm2	ABalpaaaaap	Pharyngeal muscle cell\n"+
			"m2VR_of_pm2	ABarapaaaap	Pharyngeal muscle cell\n"+
			"m3DL	MSaaapaaa	Pharyngeal muscle cell\n"+
			"m3DR	MSpaaaapa	Pharyngeal muscle cell\n"+
			"m3L	ABalpaapapp	Pharyngeal muscle cell\n"+
			"m3R	ABarapaappa	Pharyngeal muscle cell\n"+
			"m3VL	ABalpappppp	Pharyngeal muscle cell\n"+
			"m3VR	ABarapapppp	Pharyngeal muscle cell\n"+
			"m4DL	MSaaaaapp	Pharyngeal muscle cell\n"+
			"m4DR	MSpaaaapp	Pharyngeal muscle cell\n"+
			"m4L	MSaaapaap	Pharyngeal muscle cell\n"+
			"m4R	ABaraaapapp	Pharyngeal muscle cell\n"+
			"m4VL	MSaapaaaa	Pharyngeal muscle cell\n"+
			"m4VR	MSpapappp	Pharyngeal muscle cell\n"+
			"m5DL	MSaaaapap	Pharyngeal muscle cell\n"+
			"m5DR	MSpaaappa	Pharyngeal muscle cell\n"+
			"m5L	ABaraapapap	Pharyngeal muscle cell\n"+
			"m5R	ABaraapppap	Pharyngeal muscle cell\n"+
			"m5VL	MSaapaaap	Pharyngeal muscle cell\n"+
			"m5VR	MSpapaaap	Pharyngeal muscle cell\n"+
			"m6D	MSpaaappp	Pharyngeal muscle cell\n"+
			"m6VL	MSaapappa	Pharyngeal muscle cell\n"+
			"m6VR	MSpapappa	Pharyngeal muscle cell\n"+
			"m7D	MSaaaappp	Pharyngeal muscle cell\n"+
			"m7VL	MSaapaapp	Pharyngeal muscle cell\n"+
			"m7VR	MSpapaapp	Pharyngeal muscle cell\n"+
			"m8	MSaaapapp	Pharyngeal muscle cell\n"+
			"mc1DL	ABalpaapapa	Pharyngeal marginal cell\n"+
			"mc1DR	ABaraaapapa	Pharyngeal marginal cell\n"+
			"mc1V	ABalpappppa	Pharyngeal marginal cell\n"+
			"mc2DL	ABaraapaapp	Pharyngeal marginal cell\n"+
			"mc2DR	ABaraappapp	Pharyngeal marginal cell\n"+
			"mc2V	ABarapapppa	Pharyngeal marginal cell\n"+
			"mc3DL	MSaaapapa	Pharyngeal marginal cell\n"+
			"mc3DR	MSpaapapa	Pharyngeal marginal cell\n"+
			"mc3V	ABalpappapp	Pharyngeal marginal cell\n"+
			"mu_anal	ABplpppppap	Anal depressor muscle\n"+
			"VR20_VBW_muscle	ABprpppppaa	Body wall muscle\n"+
			"VL9_VBW_muscle	Capaaaa	Body wall muscle\n"+
			"VL11_VBW_muscle	Capaaap	Body wall muscle\n"+
			"VL13_VBW_muscle	Capaapa	Body wall muscle\n"+
			"VL15_VBW_muscle	Capaapp	Body wall muscle\n"+
			"DL13_DBW_muscle	Capapaa	Body wall muscle\n"+
			"DL17_DBW_muscle	Capapap	Body wall muscle\n"+
			"VL20_VBW_muscle	Capappa	Body wall muscle\n"+
			"VL22_VBW_muscle	Capappp	Body wall muscle\n"+
			"DL11_DBW_muscle	Cappaaa	Body wall muscle\n"+
			"DL18_DBW_muscle	Cappaap	Body wall muscle\n"+
			"DL19_DBW_muscle	Cappapa	Body wall muscle\n"+
			"DL23_DBW_muscle	Cappapp	Body wall muscle\n"+
			"DL20_DBW_muscle	Capppaa	Body wall muscle\n"+
			"DL22_DBW_muscle	Capppap	Body wall muscle\n"+
			"DL24_DBW_muscle	Cappppd	Body wall muscle\n"+
			"VL23_VBW_muscle	Cappppv	Body wall muscle\n"+
			"VR9_VBW_muscle	Cppaaaa	Body wall muscle\n"+
			"VR11_VBW_muscle	Cppaaap	Body wall muscle\n"+
			"VR13_VBW_muscle	Cppaapa	Body wall muscle\n"+
			"VR15_VBW_muscle	Cppaapp	Body wall muscle\n"+
			"DR13_DBW_muscle	Cppapaa	Body wall muscle\n"+
			"DR17_DBW_muscle	Cppapap	Body wall muscle\n"+
			"VR19_VBW_muscle	Cppappa	Body wall muscle\n"+
			"VR22_VBW_muscle	Cppappp	Body wall muscle\n"+
			"DR11_DBW_muscle	Cpppaaa	Body wall muscle\n"+
			"DR18_DBW_muscle	Cpppaap	Body wall muscle\n"+
			"DR19_DBW_muscle	Cpppapa	Body wall muscle\n"+
			"DR23_DBW_muscle	Cpppapp	Body wall muscle\n"+
			"DR20_DBW_muscle	Cppppaa	Body wall muscle\n"+
			"DR22_DBW_muscle	Cppppap	Body wall muscle\n"+
			"DR24_DBW_muscle	Cpppppd	Body wall muscle\n"+
			"VR24_VBW_muscle	Cpppppv	Body wall muscle\n"+
			"VL5_VBW_muscle	Daaaa	Body wall muscle\n"+
			"DL7_DBW_muscle	Daaap	Body wall muscle\n"+
			"VL4_VBW_muscle	Daapa	Body wall muscle\n"+
			"VL6_VBW_muscle	Daapp	Body wall muscle\n"+
			"DL8_DBW_muscle	Dappaa	Body wall muscle\n"+
			"DL10_DBW_muscle	Daappap	Body wall muscle\n"+
			"DL12_DBW_muscle	Daapppa	Body wall muscle\n"+
			"DL14_DBW_muscle	Daapppp	Body wall muscle\n"+
			"VR5_VBW_muscle	Dpaaa	Body wall muscle\n"+
			"DR7_DBW_muscle	Dpaap	Body wall muscle\n"+
			"VR4_VBW_muscle	Dpapa	Body wall muscle\n"+
			"VR6_VBW_muscle	Dpapp	Body wall muscle\n"+
			"VR7_VBW_muscle	Dppaa	Body wall muscle\n"+
			"DR9_DBW_muscle	Dppap	Body wall muscle\n"+
			"DR8_DBW_muscle	Dpppaa	Body wall muscle\n"+
			"DR10_DBW_muscle	Dpppap	Body wall muscle\n"+
			"DR12_DBW_muscle	Dppppa	Body wall muscle\n"+
			"DR14_DBW_muscle	Dppppp	Body wall muscle\n"+
			"VL8_VBW_muscle	MSaapppaa	Body wall muscle\n"+
			"VL10_VBW_muscle	MSaapppap	Body wall muscle\n"+
			"VL12_VBW_muscle	MSaappppa	Body wall muscle\n"+
			"VL14_VBW_muscle	MSaappppp	Body wall muscle\n"+
			"DL1_DBW_muscle	MSapaaap	Body wall muscle\n"+
			"VL1_VBW_muscle	MSapapap	Body wall muscle\n"+
			"DL2_DBW_muscle	MSapappa	Body wall muscle\n"+
			"DL3_DBW_muscle	MSapappp	Body wall muscle\n"+
			"VL2_VBW_muscle	MSappapp	Body wall muscle\n"+
			"VL3_VBW_muscle	MSapppaa	Body wall muscle\n"+
			"DL5_DBW_muscle	MSapppap	Body wall muscle\n"+
			"DL4_DBW_muscle	MSappppa	Body wall muscle\n"+
			"DL6_DBW_muscle	MSappppp	Body wall muscle\n"+
			"VL16_VBW_muscle	MSpappaa	Body wall muscle\n"+
			"VR16_VBW_muscle	MSpappap	Body wall muscle\n"+
			"VR8_VBW_muscle	MSpapppaa	Body wall muscle\n"+
			"VR10_VBW_muscle	MSpapppap	Body wall muscle\n"+
			"VR12_VBW_muscle	MSpappppa	Body wall muscle\n"+
			"VR14_VBW_muscle	MSpappppp	Body wall muscle\n"+
			"DR1_DBW_muscle	MSppaaap	Body wall muscle\n"+
			"VR1_VBW_muscle	MSppapap	Body wall muscle\n"+
			"DR2_DBW_muscle	MSppappa	Body wall muscle\n"+
			"DR3_DBW_muscle	MSppappp	Body wall muscle\n"+
			"VR2_VBW_muscle	MSpppapp	Body wall muscle\n"+
			"VR3_VBW_muscle	MSppppaa	Body wall muscle\n"+
			"DR5_DBW_muscle	MSppppap	Body wall muscle\n"+
			"DR4_DBW_muscle	MSpppppa	Body wall muscle\n"+
			"DR6_DBW_muscle	MSpppppp	Body wall muscle\n"+
			"mu_int_L	ABplpppppaa	Intestinal muscles, attach to intestine and body wall anterior to anus\n"+
			"mu_int_R	MSppaapp	Intestinal muscles, attach to intestine and body wall anterior to anus\n"+
			"mu_sph	ABprpppppap	Sphincter muscle of intestino-rectal valve\n"+
			"rect_D	ABplpappppp	Rectal epithelial cells, adjacent to intestino-rectal valve, have microvilli\n"+
			"rect_VL	ABplppppaap	Rectal epithelial cells, adjacent to intestino-rectal valve, have microvilli\n"+
			"rect_VR	ABprppppaap	Rectal epithelial cells, adjacent to intestino-rectal valve, have microvilli\n"+
			"spike	ABplppppppa	Used during embryogenesis to make tail spike, then die\n"+
			"spike	ABprppppppa	Used during embryogenesis to make tail spike, then die\n"+
			"virL	ABprpappppp	Intestino-rectal valve\n"+
			"virR	ABprpappppa	Intestino-rectal valve\n"+
			"vpi1	MSpaapapp	Pharyngo-intestinal valve\n"+
			"vpi2DL	MSaapappp	Pharyngo-intestinal valve\n"+
			"vpi2DR	MSpapappp	Pharyngo-intestinal valve\n"+
			"vpi2V	MSaappaa	Pharyngo-intestinal valve\n"+
			"vpi3D	MSaaappp	Pharyngo-intestinal valve\n"+
			"vpi3V	MSaappap	Pharyngo-intestinal valve\n";

   static Hashtable partslistHashTable;
   static Hashtable partslistHashTableReverse;
   
   @SuppressWarnings("unused")
   public PartsList(){
			partslistHashTable = new Hashtable<String, String>();
			partslistHashTableReverse = new Hashtable<String, String>();

   			String[] partslistArray = PartsList.partslist.split("\n");
			int tick =0;
			for (String partsline:partslistArray) {
				String[] chunks = partsline.split("\t");
				if (chunks.length == 3){
				    partslistHashTable.put(chunks[1].toLowerCase(), chunks[0].toLowerCase());
				    partslistHashTableReverse.put(chunks[0].toLowerCase(), chunks[1]);
				
				}
				tick++;
			}
			
			//PartsList.partslist = "";
			//PartsList.partslist = null;
			Arrays.fill(partslistArray, "");			
			partslistArray = null;
   }
   
    public static String lookupSulston(String sulstonName){
    	return (String)partslistHashTable.get(sulstonName.toLowerCase());
    }
    
    public static String lookupProper(String properName){
    	return (String)partslistHashTableReverse.get(properName.toLowerCase());
    }
}
