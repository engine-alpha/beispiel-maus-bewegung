package org.ea.beispiel.maus.beschleunigung;

import ea.*;

import java.awt.*;

public class Spiel extends Game {
	public static void main(String[] args) {
		new Spiel();
	}

	private Punkt[] letztePunkte = new Punkt[10];
	private Rechteck rechteck = new Rechteck(240, 240, 20, 20);

	public Spiel() {
		super(500, 500, "Beispiel: Maus-Beschleunigung", false, true);

		Maus maus = new Maus(new Rechteck(0, 0, 0, 0), new Punkt(0, 0), true, false) {
			{
				mausBewegungReagierbarAnmelden((dx, dy) -> {
					for(int i = letztePunkte.length - 1; i > 0; i--) {
						letztePunkte[i] = letztePunkte[i - 1];
					}

					letztePunkte[0] = new Punkt(dx, dy);
				});
			}
		};

		this.mausAnmelden(maus);

		Ticker ticker = () -> {
			System.out.println("tick");

			float x = 0, y = 0;
			int cnt = 0;

			for(Punkt p : letztePunkte) {
				if(p == null) {
					continue;
				}

				x += p.x();
				y += p.y();
				cnt++;
			}

			x /= (float) Math.max(1, cnt) * (float) 10;
			y /= (float) Math.max(1, cnt) * (float) 10;

			rechteck.verschieben(new Vektor(x, y));
		};

		this.manager.anmelden(ticker, 40);

		this.rechteck.farbeSetzen(new Color(0, 125, 250));
		this.wurzel.add(rechteck);
	}

	@Override
	public void tasteReagieren (int code) {

	}
}
