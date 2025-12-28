package com.adieu;

import com.adieu.util.Count;
import com.adieu.util.WordCounter;

import java.util.Scanner;

public class ApplicationStarter {
    public static void main(String[] args){
        showWordCountTime(10,0.75f);
        for(int i = 0;i < 3;i++){
            Long pre = 0L;
            for(int j = 0;j < 5;j++){
                pre += showWordCountTime(1,i * 0.1f + 0.6f);
            }
            System.out.println("loadFactor:" + (i * 0.1f + 0.6f) + "  time:" + pre/5);
        }
    }

    public static Long showWordCountTime(int querytimes,float loadFactor){
        Long start = System.nanoTime();
        Count c = new WordCounter(16, loadFactor);
        String s = "A Glimpse into the Future of Urban Living\n" +
                "As cities around the world continue to expand at an unprecedented rate, urban planners and architects are faced with the challenge of creating sustainable, livable, and inclusive spaces that can accommodate the growing population while minimizing the environmental footprint. The future of urban living is not just about constructing taller buildings or wider roads; it is about reimagining the very essence of how we interact with our surroundings, connect with one another, and coexist with nature in a densely populated world.\n" +
                "One of the most promising trends in modern urban design is the concept of vertical forests—skyscrapers adorned with thousands of trees, shrubs, and plants that not only enhance the aesthetic appeal of the city but also play a crucial role in purifying the air, reducing noise pollution, and regulating temperature. These green towers act as natural air filters, absorbing carbon dioxide and releasing oxygen, while also providing a habitat for birds, insects, and other small creatures, thereby restoring a sense of biodiversity to urban areas that have long been dominated by concrete and steel.\n" +
                "In addition to vertical forests, smart cities are emerging as a key solution to the challenges of urbanization. Equipped with advanced sensors, artificial intelligence, and the Internet of Things (IoT), smart cities are designed to optimize resource usage, improve public services, and enhance the quality of life for their residents. For example, smart traffic management systems can monitor real-time traffic flow and adjust traffic lights accordingly, reducing congestion and cutting down on commute times. Smart energy grids can balance supply and demand by integrating renewable energy sources such as solar and wind power, making cities more resilient to power outages and less dependent on fossil fuels. Smart waste management systems can track garbage collection routes and optimize them to reduce fuel consumption and greenhouse gas emissions, while also encouraging residents to recycle more effectively through incentives and real-time feedback.\n" +
                "Another critical aspect of future urban living is the emphasis on mixed-use developments—neighborhoods where residential, commercial, and recreational spaces are integrated into a single, walkable area. This approach reduces the need for long commutes, as people can live, work, shop, and relax within a short distance from their homes. Mixed-use developments also foster a stronger sense of community, as residents have more opportunities to interact with one another in parks, cafes, and public squares. Moreover, by reducing the reliance on cars, these neighborhoods help to decrease air pollution and traffic accidents, making cities safer and healthier for everyone.\n" +
                "However, the transition to sustainable urban living is not without its obstacles. One of the biggest challenges is the high cost of implementing green technologies and infrastructure, which can be a barrier for many cities, especially those in developing countries. Another challenge is the need to balance urban development with the preservation of historical and cultural heritage. Many old cities have unique architectural styles and cultural landmarks that are at risk of being demolished to make way for new construction. Finding ways to integrate modern amenities into historic neighborhoods without destroying their character is a delicate task that requires careful planning and collaboration between architects, historians, and local communities.\n" +
                "Furthermore, ensuring that urban development is inclusive is essential for creating cities that work for everyone. Too often, urbanization leads to gentrification, where low-income residents are displaced from their homes as property values rise and new developments cater to wealthier individuals. To prevent this, urban planners must prioritize affordable housing, public transportation, and access to education and healthcare for all residents, regardless of their income or background. Inclusive cities are those where every person has the opportunity to thrive, regardless of where they live or how much money they make.\n" +
                "As we look ahead to the future of urban living, it is clear that the cities of tomorrow will be defined by their ability to adapt, innovate, and prioritize the needs of both people and the planet. From vertical forests and smart technologies to mixed-use neighborhoods and inclusive policies, the vision of sustainable urban living is within our reach—but it will require a collective effort from governments, businesses, and individuals to turn this vision into reality. The choices we make today will shape the cities of tomorrow, and it is up to us to ensure that those cities are places where we can all live, work, and flourish in harmony with nature and with one another.\n" +
                "The future of urban living is not a distant dream; it is a journey that we are embarking on right now. Every decision we make—whether it is to plant a tree in a local park, use public transportation instead of a car, or advocate for affordable housing—contributes to the creation of a better, more sustainable world for generations to come. As cities continue to evolve, let us remember that the true measure of a great city is not its skyline or its GDP, but the quality of life it provides for its residents and the legacy it leaves for the future.";
        c.insert(s);
        for(int i = 0;i < querytimes;i++){
            c.show();
        }
        Long end = System.nanoTime();
        return end - start;
    }
}
