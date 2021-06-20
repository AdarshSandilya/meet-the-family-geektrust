package com.adarsh.geektrust.strategies;

import com.adarsh.geektrust.models.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DaughterStrategyTest {
    @Mock
    Person person;

    @InjectMocks
    DaughterStrategy strategy;

    @Test
    void apply_should_fetch_daughters_of_a_given_person() {
        strategy.apply(person);
        verify(person, times(1)).getDaughters();
    }
}