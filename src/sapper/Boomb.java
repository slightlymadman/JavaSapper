package sapper;

class Boomb
{
    private Matrix boombMap;
    private int totalBombs;

    Boomb (int totalBombs)
    {
        this.totalBombs = totalBombs;
        fixBoombCount();
    }

    void start ()
    {
        boombMap = new Matrix (Box.ZERO);
        for (int i = 0; i < totalBombs; i++)
            placeBoomb();

    }

    Box get (Coord coord)
    {
        return boombMap.get(coord);
    }

    private void fixBoombCount()
    {
        int maxBoombs = Ranges.getSize().x * Ranges.getSize().y / 2;
        if (totalBombs > maxBoombs)
            totalBombs = maxBoombs;
    }

    private  void placeBoomb ()
    {
        while (true)
        {
            Coord coord = Ranges.getRandomCoord();
            if (Box.BOMB == boombMap.get(coord))
                continue;
            boombMap.set (coord, Box.BOMB);
            incNumbersAroundBoomb(coord);
            break;
        }

    }

    private void incNumbersAroundBoomb (Coord coord)
    {
        for (Coord around : Ranges.getCoordAround(coord))
            if (Box.BOMB != boombMap.get(around))
                boombMap.set(around, boombMap.get(around).getNextNumberBox());

    }

    int getTotalBoombs()
    {
        return totalBombs;
    }
}
